package dl.doctracer.security

import dl.doctracer.exception.GlobalExceptionHandler
import io.jsonwebtoken.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${app.jwt.secret}")
    private val jwtSecret: String,

    @Value("\${app.jwt.expiration-in-ms}")
    private val jwtExpirationInMs: Long
) {
    private val logger: Logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    fun generateToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetailsImpl
        val roles = userPrincipal.authorities.map { it.authority }

        logger.info("Assigning roles: {} to username: {} in jwt", roles, userPrincipal.username)

        val now = Date()
        val expiryDate = Date(now.time + jwtExpirationInMs)

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .claim("roles", roles)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getUsernameFromJWT(token: String): String {
        val claims = Jwts.parser()
            .setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .body

        return claims.subject
    }

    fun validateToken(authToken: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: Exception) {
//            TODO handle all exceptions (probably in global exception handler)
        }
        return false
    }
}
