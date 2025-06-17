package com.aluracursos.conversormonedas;

public class ApiCallException extends RuntimeException {
    public ApiCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
