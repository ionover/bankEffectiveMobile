package org.example.bank2.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(CustomRestExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .toList();

        ApiErrorModel errorModel = new ApiErrorModel(
                HttpStatus.BAD_REQUEST,
                "Ошибка валидации",
                errors
        );

        log.error("VALIDATION ERROR: {}", errors);

        return new ResponseEntity<>(errorModel, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(final RuntimeException e) {
        BadRequestException ex = (BadRequestException) e;

        ApiErrorModel errorModel = new ApiErrorModel(BAD_REQUEST, ex.getMessage());

        log.error("BAD REQUEST: {}", errorModel.getMessage());

        return new ResponseEntity<>(errorModel, new HttpHeaders(), errorModel.getStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(final RuntimeException e) {
        UnauthorizedException ex = (UnauthorizedException) e;

        ApiErrorModel errorModel = new ApiErrorModel(UNAUTHORIZED, ex.getMessage());

        log.error("UNAUTHORIZED: {}", errorModel.getMessage());

        return new ResponseEntity<>(errorModel, new HttpHeaders(), errorModel.getStatus());
    }
}
