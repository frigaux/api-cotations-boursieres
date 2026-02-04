package fr.fabien.api.cotations.restcontroller.exceptions;

import org.springframework.http.HttpStatus;

public class ClientErrorException extends RuntimeException {
    public HttpStatus httpStatus;

    public ClientErrorException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
