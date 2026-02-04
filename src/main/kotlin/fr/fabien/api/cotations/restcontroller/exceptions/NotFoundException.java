package fr.fabien.api.cotations.restcontroller.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ClientErrorException {
    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, null);
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
