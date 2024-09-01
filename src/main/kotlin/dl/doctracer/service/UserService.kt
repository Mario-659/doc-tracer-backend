package dl.doctracer.service

import dl.doctracer.dto.LoginRequest
import dl.doctracer.dto.LoginResponse
import dl.doctracer.dto.RegisterRequest
import dl.doctracer.model.User
import dl.doctracer.repository.UserRepository
import dl.doctracer.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun register(registerRequest: RegisterRequest): User {

        if (userRepository.existsByUsername(registerRequest.username)) {
            throw IllegalArgumentException("Username '${registerRequest.username}' already exists")
        }

        if (userRepository.existsByEmail(registerRequest.email)) {
            throw IllegalArgumentException("Email '${registerRequest.email}' is already taken")
        }

        val user = User(
            username = registerRequest.username,
            password = passwordEncoder.encode(registerRequest.password),
            email = registerRequest.email,
            firstName = registerRequest.firstName,
            lastName = registerRequest.lastName,
            isSuperuser = false, // TODO make possibility to create superuser
            isActive = true
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
        val token = jwtTokenProvider.generateToken(authentication)
        return LoginResponse(token)
    }

    fun findAll(): List<User> = userRepository.findAll()

    fun findById(id: Int): User? = userRepository.findById(id).orElse(null)

    fun findByUsername(username: String): User? = userRepository.findByUsername(username)

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)

    @Transactional
    fun save(user: User): User = userRepository.save(user)

    @Transactional
    fun deleteById(id: Int) = userRepository.deleteById(id)
}
