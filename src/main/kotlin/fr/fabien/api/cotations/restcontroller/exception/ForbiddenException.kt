package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.HttpStatus

class ForbiddenException(override val message: String? = null) : ClientErrorException(HttpStatus.FORBIDDEN, message) {
}