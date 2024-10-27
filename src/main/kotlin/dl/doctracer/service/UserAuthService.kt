package dl.doctracer.service

import dl.doctracer.dto.auth.LoginRequest
import dl.doctracer.dto.auth.LoginResponse
import dl.doctracer.dto.auth.PasswordChangeRequest
import dl.doctracer.dto.auth.RegisterRequest
import dl.doctracer.exception.EntityNotFoundException
import dl.doctracer.exception.UnauthorizedException
import dl.doctracer.model.User
import dl.doctracer.repository.UserRepository
import dl.doctracer.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class UserAuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun register(registerRequest: RegisterRequest): User {

        if (userRepository.existsByUsername(registerRequest.username)) {
            throw IllegalArgumentException("Username ${registerRequest.username} already exists")
        }

        if (userRepository.existsByEmail(registerRequest.email)) {
            throw IllegalArgumentException("Email ${registerRequest.email} is already taken")
        }

        val user = User(
            username = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password),
            email = registerRequest.email,
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            isActive = false
        )

        return userRepository.save(user)
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )

        userRepository
            .findByUsername(loginRequest.username)
            ?.copy(lastLogin = Instant.now())
            ?.let { userRepository.save(it) }
            ?: throw EntityNotFoundException()

        val token = jwtTokenProvider.generateToken(authentication)

        return LoginResponse(token)
    }

//    TODO create separate controller for user updates stuff and move fun below there, now everyone can send request to change password without logging in
    fun changePassword(passwordChangeReq: PasswordChangeRequest) {
        val authentication = SecurityContextHolder.getContext().authentication

        val loggedInUser = userRepository.findByUsername(authentication.name) ?:
            throw UnauthorizedException("Could not find user ${authentication.name} in database")

        if (!passwordEncoder.matches(passwordChangeReq.oldPassword, loggedInUser.password)) {
            throw UnauthorizedException("Old password is incorrect")
        }

        val updatedUser = loggedInUser.copy(password = passwordEncoder.encode(passwordChangeReq.newPassword))
        userRepository.save(updatedUser)
    }
}
