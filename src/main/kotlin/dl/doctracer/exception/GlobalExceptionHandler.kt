package dl.doctracer.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.map { it.defaultMessage ?: "Invalid field" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(ex: BadCredentialsException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password")
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleIllegalArgumentException(ex: HttpRequestMethodNotSupportedException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ex.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<Any> {
        logger.error(ex.message)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred")
    }
}