package org.example.bank2.util;

import org.example.bank2.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class CardNumberProtector {

    private static final String AES_ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORMATION = "AES/GCM/NoPadding";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final int GCM_TAG_LENGTH_BITS = 128;
    private static final int GCM_IV_LENGTH_BYTES = 12;

    private final SecretKeySpec encryptionKey;
    private final SecretKeySpec hashKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public CardNumberProtector(@Value("${card.number.encryption-secret}") String encryptionSecret,
                               @Value("${card.number.hash-secret}") String hashSecret) {
        validateSecret(encryptionSecret, "card.number.encryption-secret");
        validateSecret(hashSecret, "card.number.hash-secret");
        this.encryptionKey = new SecretKeySpec(sha256(encryptionSecret), AES_ALGORITHM);
        this.hashKey = new SecretKeySpec(hashSecret.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
    }

    public ProtectedCardNumber protect(String rawNumber) {
        String normalizedNumber = normalize(rawNumber);

        return new ProtectedCardNumber(
                encrypt(normalizedNumber),
                hash(normalizedNumber),
                last4(normalizedNumber)
        );
    }

    public CardNumberFilter filter(String rawNumber) {
        if (rawNumber == null || rawNumber.isBlank()) {
            return new CardNumberFilter(null, null);
        }

        String normalizedNumber = normalize(rawNumber);
        if (normalizedNumber.length() == 4) {
            return new CardNumberFilter(null, normalizedNumber);
        }

        return new CardNumberFilter(hash(normalizedNumber), null);
    }

    public String restore(String encryptedNumber) {
        if (encryptedNumber == null || encryptedNumber.isBlank()) {
            throw new BadRequestException("Зашифрованный номер карты не задан");
        }

        try {
            byte[] encryptedPayload = Base64.getDecoder().decode(encryptedNumber);
            if (encryptedPayload.length <= GCM_IV_LENGTH_BYTES) {
                throw new BadRequestException("Номер карты не удалось расшифровать");
            }
            ByteBuffer buffer = ByteBuffer.wrap(encryptedPayload);
            byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
            buffer.get(iv);
            byte[] cipherText = new byte[buffer.remaining()];
            buffer.get(cipherText);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, encryptionKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));

            return new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException | GeneralSecurityException e) {
            throw new BadRequestException("Номер карты не удалось расшифровать");
        }
    }

    public String mask(String last4) {
        if (last4 == null || !last4.matches("\\d{4}")) {
            throw new BadRequestException("Некорректные последние четыре цифры номера карты");
        }

        return "**** **** **** " + last4;
    }

    private String normalize(String rawNumber) {
        if (rawNumber == null) {
            throw new BadRequestException("Номер карты не задан");
        }

        String normalizedNumber = rawNumber.replaceAll("[\\s-]", "");
        if (!normalizedNumber.matches("\\d{4,20}")) {
            throw new BadRequestException("Номер карты должен содержать от 4 до 20 цифр");
        }

        return normalizedNumber;
    }

    private String encrypt(String normalizedNumber) {
        try {
            byte[] iv = new byte[GCM_IV_LENGTH_BYTES];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, new GCMParameterSpec(GCM_TAG_LENGTH_BITS, iv));
            byte[] cipherText = cipher.doFinal(normalizedNumber.getBytes(StandardCharsets.UTF_8));

            ByteBuffer payload = ByteBuffer.allocate(iv.length + cipherText.length);
            payload.put(iv);
            payload.put(cipherText);

            return Base64.getEncoder().encodeToString(payload.array());
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Card number encryption failed", e);
        }
    }

    private String hash(String normalizedNumber) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(hashKey);

            return toHex(mac.doFinal(normalizedNumber.getBytes(StandardCharsets.UTF_8)));
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Card number hashing failed", e);
        }
    }

    private String last4(String normalizedNumber) {
        return normalizedNumber.substring(normalizedNumber.length() - 4);
    }

    private byte[] sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Unable to build card encryption key", e);
        }
    }

    private String toHex(byte[] bytes) {
        char[] hexDigits = "0123456789abcdef".toCharArray();
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte currentByte : bytes) {
            hex.append(hexDigits[(currentByte >> 4) & 0x0f]);
            hex.append(hexDigits[currentByte & 0x0f]);
        }

        return hex.toString();
    }

    private void validateSecret(String secret, String propertyName) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(propertyName + " must not be blank");
        }
    }

    public record ProtectedCardNumber(String encrypted, String hash, String last4) {
    }

    public record CardNumberFilter(String hash, String last4) {
    }
}
