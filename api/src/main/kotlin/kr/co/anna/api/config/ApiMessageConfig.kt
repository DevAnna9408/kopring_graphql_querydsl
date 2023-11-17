package kr.co.anna.api.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

/**
 * api 다국어 메세지 설정
 */
@Configuration
class ApiMessageConfig {

    @Autowired
    var messageSource: ReloadableResourceBundleMessageSource? = null

    @Bean
    fun addMessageBaseName() {
        messageSource!!.addBasenames("classpath:/api-messages/message")
    }

}
