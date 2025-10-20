package ru.silonov.bing.exception

import lombok.extern.slf4j.Slf4j
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@Slf4j
@ControllerAdvice
class BingExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(NoSuchElementException::class)
    fun handleResourceNotFoundException(ex: NoSuchElementException, request: WebRequest): ResponseEntity<String> {
        logger.info(ex.localizedMessage)
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAuthorizationDeniedException(ex: AuthorizationDeniedException, request: WebRequest): ResponseEntity<String> {
        logger.error(ex.localizedMessage)
        return ResponseEntity(ex.message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(ex: Exception, request: WebRequest): ResponseEntity<String> {
        logger.error("Error occurred while processing request", ex)
        return ResponseEntity(ex.message, HttpStatus.INTERNAL_SERVER_ERROR)
    }

}