package dl.doctracer.controller

import dl.doctracer.dto.auth.LoginRequest
import dl.doctracer.dto.auth.PasswordChangeRequest
import dl.doctracer.dto.auth.RegisterRequest
import dl.doctracer.service.UserAuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userAuthService: UserAuthService
) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        userAuthService.register(registerRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully")
    }

    @PostMapping("/login")
    fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val loginResponse = userAuthService.login(loginRequest)
        return ResponseEntity.ok(loginResponse)
    }

    @PutMapping("/change-password")
    fun changePassword(
        @RequestBody passwordChangeDTO: PasswordChangeRequest
    ): ResponseEntity<String> {
        userAuthService.changePassword(passwordChangeDTO)
        return ResponseEntity.ok().build()
    }
}
