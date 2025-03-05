package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class ClientErrorExceptionHandler {
    @ExceptionHandler(ClientErrorException::class)
    private fun handleClientErrorException(e: ClientErrorException): ResponseEntity<ClientError> {
        return ResponseEntity<ClientError>(
            ClientError(e.message ?: e.httpStatus.name),
            e.httpStatus
        )
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        MethodArgumentNotValidException::class
    )
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): Map<String, String> {
        return e.bindingResult.allErrors
            .associateBy(
                { (it as FieldError).field },
                { it.getDefaultMessage()!! }
            )
    }
}