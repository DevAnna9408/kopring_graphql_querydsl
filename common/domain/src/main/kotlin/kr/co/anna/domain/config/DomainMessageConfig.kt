package kr.co.anna.domain.config

import kr.co.anna.domain._common.DomainMessageUtil
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.MessageSourceAccessor
import org.springframework.context.support.ReloadableResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.*

@Configuration
class DomainMessageConfig {

    @Bean
    fun localeResolverDomain(): LocaleResolver? {
        val localeResolver = AcceptHeaderLocaleResolver()
        localeResolver.defaultLocale = Locale.KOREA //언어&국가정보가 없는 경우 한국으로 인식
        return localeResolver
    }


    @Bean
    fun messageSourceDomain(): MessageSource? {
        val messageSource = ReloadableResourceBundleMessageSource()
        messageSource.setBasenames("classpath:/domain-messages/message")
        messageSource.setDefaultEncoding("utf-8")
        messageSource.setCacheSeconds(180) // 리로딩 간격
        Locale.setDefault(Locale.KOREA) // 제공하지 않는 언어로 요청이 들어왔을 때 MessageSource에서 사용할 기본 언어정보.
        return messageSource
    }

    @Bean
    fun messageSourceAccessorDomain(): MessageSourceAccessor {
        return MessageSourceAccessor(messageSourceDomain()!!)
    }

    @Bean
    fun messageUtilsDomain() {
        DomainMessageUtil.messageSourceAccessorDomain = messageSourceAccessorDomain()!!
    }

}
