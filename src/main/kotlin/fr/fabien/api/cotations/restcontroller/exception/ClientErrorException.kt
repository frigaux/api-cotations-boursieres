package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.HttpStatus

open class ClientErrorException(val httpStatus: HttpStatus, override val message: String? = null) : RuntimeException() {
}