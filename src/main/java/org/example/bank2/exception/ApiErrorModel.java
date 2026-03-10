package org.example.bank2.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

public class ApiErrorModel {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    private int status;

    private String message;

    private List<String> errors;

    public ApiErrorModel() {
    }

    public ApiErrorModel(HttpStatus httpStatus, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus.value();
        this.message = message;
    }

    public ApiErrorModel(HttpStatus httpStatus, String message, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus.value();
        this.message = message;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
