package fr.fabien.api.cotations.restcontroller.exceptions;

import org.springframework.http.HttpStatus;

public class NotAuthorizedException extends ClientErrorException {
    public NotAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED, null);
    }

    public NotAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}