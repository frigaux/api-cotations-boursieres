package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.HttpStatus

class BadRequestException(override val message: String? = null) : ClientErrorException(HttpStatus.BAD_REQUEST, message) {
}