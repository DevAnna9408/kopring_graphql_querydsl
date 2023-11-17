package kr.co.anna.lib.config

import kr.co.anna.lib.utils.MessageUtil
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@Configuration
class MessageConfig {

    @Bean
    fun localeResolver(): LocaleResolver {
        val localeResolver = AcceptHeaderLocaleResolver()
        localeResolver.defaultLocale = Locale.KOREA //언어&국가정보가 없는 경우 한국으로 인식
        return localeResolver
    }

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasenames("classpath:/messages/message")
        messageSource.setDefaultEncoding("utf-8")
        messageSource.setCacheSeconds(180) // 리로딩 간격
        Locale.setDefault(Locale.KOREA) // 제공하지 않는 언어로 요청이 들어왔을 때 MessageSource에서 사용할 기본 언어정보.
        return messageSource
    }

    @Bean
    fun messageSourceAccessor(): MessageSourceAccessor {
        return MessageSourceAccessor(messageSource()!!)
    }

    @Bean
    fun messageUtils() {
        MessageUtil.messageSourceAccessor = messageSourceAccessor()!!
    }

}
