package kr.co.anna.api.interceptor

import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
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
