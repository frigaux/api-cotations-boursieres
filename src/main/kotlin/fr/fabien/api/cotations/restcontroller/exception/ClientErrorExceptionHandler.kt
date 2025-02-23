package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ClientErrorExceptionHandler {
    @ExceptionHandler(ClientErrorException::class)
    private fun handle(e: ClientErrorException): ResponseEntity<ClientError> {
        return ResponseEntity<ClientError>(
            ClientError(e.message?: e.httpStatus.name),
            e.httpStatus
        )
    }
}