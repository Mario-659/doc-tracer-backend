package dl.doctracer.controller

import dl.doctracer.dto.LoginRequest
import dl.doctracer.dto.RegisterRequest
import dl.doctracer.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        userService.register(registerRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val loginResponse = userService.login(loginRequest)
        return ResponseEntity.ok(loginResponse)
    }
}
