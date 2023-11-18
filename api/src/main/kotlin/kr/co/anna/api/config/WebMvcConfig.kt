package kr.co.anna.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import kr.co.anna.api.interceptor.PersonalInfoLoggingInterceptor
import kr.co.anna.domain.repository.log.QueryLogRepository
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 스프링 MVC 설정
 *
 * 현재는 interceptor 설정만 있으나 이 외에도 여러가지 설정 가능
 */
@Configuration
class WebMvcConfig() : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {

    }
}
