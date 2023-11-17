package kr.co.anna.lib.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthFilter(private val jwtProcessor: JwtProcessor) :
    OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwtFromRequest = getJwtFromRequest(request)
        try {
            if (!jwtFromRequest.isNullOrBlank()) {
                SecurityContextHolder.getContext().authentication =
                    jwtProcessor.extractAuthentication(jwtFromRequest) // SecurityContext 에 Authentication 객체를 저장합니다.
            }
        } catch (e: Exception) {
            SecurityContextHolder.clearContext()
        }
        filterChain.doFilter(request, response)
    }

    private val BEARER_PREFIX = "Bearer "

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.startsWith(BEARER_PREFIX, true)) {
            bearerToken.substring(BEARER_PREFIX.length)
        } else null
    }

}
