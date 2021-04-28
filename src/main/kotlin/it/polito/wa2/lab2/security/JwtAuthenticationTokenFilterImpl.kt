package it.polito.wa2.lab2.security

import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationTokenFilterImpl(val jwtUtils: JwtUtils): OncePerRequestFilter(), JwtAuthenticationTokenFilter {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val jwt: String? = jwtUtils.extractJwt(request)

        try {
            if(jwt != null && jwtUtils.validateJwtToken(jwt)){
                val userDetails: UserDetails = jwtUtils.getDetailsFromJwtToken(jwt)
                val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }catch(ex: InvalidJwtAuthException){
            response.status = HttpStatus.UNAUTHORIZED.value()
            response.contentType = "application/json"
            response.writer.write("{\"error\": \"${ex.message}\"}")
            return
        }

        filterChain.doFilter(request, response)
    }
}