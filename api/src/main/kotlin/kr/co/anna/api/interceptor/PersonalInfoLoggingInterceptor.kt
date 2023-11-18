package kr.co.anna.api.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.anna.domain.model.log.QueryLog
import kr.co.anna.domain.repository.log.QueryLogRepository
import kr.co.anna.lib.security.SecurityUtil
import org.slf4j.LoggerFactory
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 개인정보 열람 정보 로깅
 * TODO: 프로젝트에 맞게 변경필요
 * CustomServletWrapperFilter 참고
 */
class PersonalInfoLoggingInterceptor() : HandlerInterceptor {


    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
    }
}
