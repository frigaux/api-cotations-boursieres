package fr.fabien.api.cotations.restcontroller.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLIntegrityConstraintViolationException


@RestControllerAdvice
class ClientErrorExceptionHandler {
    // impossible de convertir un @RequestBody (exemple : valeur énumération inexistantes)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    private fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ClientError> {
        return ResponseEntity<ClientError>(
            ClientError(e.message ?: e.toString()),
            HttpStatus.BAD_REQUEST
        )
    }

    // validation des jakarta.validation.constraints par org.hibernate.validator.internal.engine.ValidatorImpl
    // voir : @Validated @RequestBody
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


    // traitement de mes exceptions étendant ClientErrorException
    @ExceptionHandler(ClientErrorException::class)
    private fun handleClientErrorException(e: ClientErrorException): ResponseEntity<ClientError> {
        return ResponseEntity<ClientError>(
            ClientError(e.message ?: e.httpStatus.name),
            e.httpStatus
        )
    }

    // traitement des contraintes SQL non respectées (exemple : colonne nom unique)
    @ExceptionHandler(
        SQLIntegrityConstraintViolationException::class
    )
    fun handleSQLIntegrityConstraintViolationException(e: SQLIntegrityConstraintViolationException): ResponseEntity<ClientError> {
        return ResponseEntity<ClientError>(
            ClientError(e.message ?: e.errorCode.toString()),
            HttpStatus.CONFLICT
        )
    }
}