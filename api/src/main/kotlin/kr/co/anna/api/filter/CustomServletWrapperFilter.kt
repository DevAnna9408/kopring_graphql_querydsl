package kr.co.anna.api.filter

import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * http req, res 를 여러번 읽을 수 있도록 ContentCaching Wrapper 사요
 *
 * 참고: https://hirlawldo.tistory.com/44
 */
@Component
class CustomServletWrapperFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val wrappingRequest = ContentCachingRequestWrapper(request)
        val wrappingResponse = ContentCachingResponseWrapper(response)
        filterChain.doFilter(wrappingRequest, wrappingResponse)
        wrappingResponse.copyBodyToResponse();
    }
}
