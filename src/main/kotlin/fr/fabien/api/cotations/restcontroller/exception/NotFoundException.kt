package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.HttpStatus

class NotFoundException(override val message: String? = null) : ClientErrorException(HttpStatus.NOT_FOUND, message) {
}