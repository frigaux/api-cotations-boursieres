package fr.fabien.api.cotations.restcontroller.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ClientErrorException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, null);
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}