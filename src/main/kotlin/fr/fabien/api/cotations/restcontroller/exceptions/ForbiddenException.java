package fr.fabien.api.cotations.restcontroller.exceptions;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends ClientErrorException {
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN, null);
    }

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}