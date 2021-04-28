package it.polito.wa2.lab2.security

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import it.polito.wa2.lab2.dto.UserDetailsDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest

@Component
class JwtUtilsImpl(
    @Value("\${application.jwt.jwtHeader}")
    val header: String,
    @Value("\${application.jwt.jwtHeaderStart}")
    val headerStart: String,
    @Value("\${application.jwt.jwtSecret}")
    val secret: String,
    @Value("\${application.jwt.jwtExpirationMs}")
    val expiration: String
):JwtUtils {

    override fun generateJwtToken(authentication: Authentication): String {

        val claims: Claims = Jwts
            .claims(mapOf("roles" to authentication.authorities.map { it.authority }.fold("") { a, b -> "$a:$b" }.removePrefix(":")))
            .setSubject((authentication.principal as UserDetailsDTO).username)

        val now = Date()

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + expiration.toInt())) // 1 hour
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .compact()
    }

    override fun validateJwtToken(authToken: String): Boolean {
        return try {
            val claims: Claims = Jwts.parserBuilder().setSigningKey(secret.toByteArray()).build().parseClaimsJws(authToken).body
            if (claims.expiration.after(Date()) && claims.containsKey("roles") && claims.subject != null) true else throw InvalidJwtAuthException()
        }catch(ex: JwtException){
            throw InvalidJwtAuthException()
        }
    }

    override fun getDetailsFromJwtToken(authToken: String): UserDetailsDTO {
        try {
            val claims: Claims = Jwts.parserBuilder().setSigningKey(secret.toByteArray()).build().parseClaimsJws(authToken).body
            return UserDetailsDTO(claims.subject, (claims["roles"] as String).split(":").map { GrantedAuthority { it } }.toMutableSet())
        }catch(ex: JwtException){
            throw InvalidJwtAuthException()
        }catch(ex: IllegalArgumentException){
            throw InvalidJwtAuthException()
        }catch(ex: ClassCastException){
            throw InvalidJwtAuthException()
        }

    }

    override fun extractJwt(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader(header)
        return if (bearerToken != null && bearerToken.startsWith(headerStart)) bearerToken.removePrefix(headerStart) else null
    }
}