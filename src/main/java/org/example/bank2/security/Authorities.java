package org.example.bank2.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class Authorities {

    public static final String ADMIN = "ADMIN";

    public static final String USER = "USER";

    public static final String ADMIN_AUTHORITY = "hasAuthority('" + ADMIN + "')";

    public static final String HAS_ANY_AUTHORITY = "hasAnyAuthority('" + ADMIN + "','" + USER + "')";

    public static boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities()
                             .stream()
                             .anyMatch(grantedAuthority -> Objects
                                     .equals(grantedAuthority.getAuthority(),
                                             ADMIN));
    }

    public static String getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
