package fr.fabien.api.cotations.restcontroller.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ClientErrorException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
