package kr.co.anna.api.config

import kr.co.anna.domain._common.EnumMapper
import kr.co.anna.domain.model.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * enum 목록 조회시 사용할 enum등록
 */
@Configuration
class EnumMapperConfig {

    @Bean
    fun enumMapper(): EnumMapper {
        val enumMapper = EnumMapper()
        enumMapper.put("ROLE", Role::class.java)
        return enumMapper
    }
}
