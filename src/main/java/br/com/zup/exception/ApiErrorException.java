package br.com.zup.exception;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends Exception {

    private final Integer code;

    public ApiErrorException(HttpStatus status, String message) {
        super(message);
        this.code = status.value();
    }

    public Integer getCode() {
        return code;
    }
}
