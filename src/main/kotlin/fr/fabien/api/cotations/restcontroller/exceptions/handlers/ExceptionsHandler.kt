package fr.fabien.api.cotations.restcontroller.exceptions.handlers

import fr.fabien.api.cotations.restcontroller.exceptions.ClientErrorException
import org.springframework.dao.InvalidDataAccessApiUsageException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLIntegrityConstraintViolationException


@RestControllerAdvice
class ExceptionsHandler {
    // impossible de convertir un @RequestBody (valeur énumération inexistante, ...)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    private fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<DtoErreurHttp> {
        return ResponseEntity<DtoErreurHttp>(
            DtoErreurHttp(e.message ?: e.toString()),
            HttpStatus.BAD_REQUEST
        )
    }

    // validation des jakarta.validation.constraints par org.hibernate.validator.internal.engine.ValidatorImpl
    // (@Validated @RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<DtoErreurHttp> {
        return ResponseEntity<DtoErreurHttp>(
            DtoErreurHttp(
                e.bindingResult.allErrors
                    .associateBy(
                        { (it as FieldError).field },
                        { it.getDefaultMessage()!! }
                    ).toString()),
            HttpStatus.BAD_REQUEST
        )
    }

    // traitement des exceptions étendant ClientErrorException
    @ExceptionHandler(ClientErrorException::class)
    private fun handleClientErrorException(e: ClientErrorException): ResponseEntity<DtoErreurHttp> {
        return ResponseEntity<DtoErreurHttp>(
            DtoErreurHttp(e.message ?: e.httpStatus.name),
            e.httpStatus
        )
    }

    // traitement des contraintes SQL non respectées (exemple : colonne nom unique)
    @ExceptionHandler(
        SQLIntegrityConstraintViolationException::class
    )
    fun handleSQLIntegrityConstraintViolationException(e: SQLIntegrityConstraintViolationException): ResponseEntity<DtoErreurHttp> {
        return ResponseEntity<DtoErreurHttp>(
            DtoErreurHttp(e.message ?: e.errorCode.toString()),
            HttpStatus.CONFLICT
        )
    }

    // traitement des exceptions levées lors de la persistence (exemple : @PrePersist avec ExpressionAlerteChecker)
    @ExceptionHandler(
        InvalidDataAccessApiUsageException::class
    )
    fun handleInvalidDataAccessApiUsageException(e: InvalidDataAccessApiUsageException): ResponseEntity<DtoErreurHttp> {
        return ResponseEntity<DtoErreurHttp>(
            DtoErreurHttp(e.message ?: e.toString()),
            HttpStatus.BAD_REQUEST
        )
    }
}