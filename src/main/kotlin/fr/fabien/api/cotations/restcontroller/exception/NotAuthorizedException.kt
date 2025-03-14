package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.HttpStatus

class NotAuthorizedException(override val message: String? = null) : ClientErrorException(HttpStatus.UNAUTHORIZED, message) {
}