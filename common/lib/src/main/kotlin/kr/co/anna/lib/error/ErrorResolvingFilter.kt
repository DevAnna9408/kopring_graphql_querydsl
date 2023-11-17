package kr.co.anna.lib.error

import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Filter Chain 안에서 발생한 예외를 GlobalExceptionHandler 에서 처리할 수 있게 해주는 필터
 * Security Config 에 추가할 때 필터 체인 앞쪽에 놔야 더 넓은 범위의 필터 체인 커버 가능
 * 필터 체인 목록: https://docs.spring.io/spring-security/site/docs/current/reference/html5/#filter-stack
 */
class ErrorResolvingFilter(
    private val handlerExceptionResolver: HandlerExceptionResolver
): OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            log.error("Exception in Filter Chain:", e)
            handlerExceptionResolver.resolveException(request, response, null, e)
        }
    }
}
