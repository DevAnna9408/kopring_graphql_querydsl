package kr.co.anna.api.config

import kr.co.anna.lib.security.AuthUser
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.util.*

/**
 * JPA 설정 클래스
 * 2개의 빈은 디비 테이블에 createdAt, createdBy 등 audit 정보를 자동으로 저장하는데 사용
 */
@Configuration
@EntityScan(basePackages = ["kr.co.anna.domain"])  // common.domain 까지 명시하면 에러 발생
@EnableJpaRepositories(basePackages = ["kr.co.anna.domain"])  // common.domain 까지 명시하면 에러 발생
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
class JpaConfig {

    @Bean
    fun auditingDateTimeProvider() = DateTimeProvider {
        Optional.of(LocalDateTime.now())
    }

    @Bean
    fun auditorAwareProvider() = AuditorAware {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication == null) {
            Optional.empty()
        } else when (val principal: Any = authentication.principal) {
            is String -> Optional.of(principal)
            is AuthUser -> Optional.of(principal.userId)
            else -> Optional.empty()
        }
    }
}
