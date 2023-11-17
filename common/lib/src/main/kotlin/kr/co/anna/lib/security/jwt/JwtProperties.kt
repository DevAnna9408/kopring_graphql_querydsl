package kr.co.anna.lib.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "jwt") //FIXME: YML과 매핑확인
@ConstructorBinding
data class JwtProperties (
    val base64EncodedSecret: String,  // JWT 생성/파싱에 사용하는 비밀키
    val tokenDurationHr: Int? = 24,  // 사용자 토큰 유효 시간
    val swaggerTokenDurationHr: Int? = 120,  // 스웨거 토큰 유효 시간
    val generatorEnabled: Boolean = false,  // 스웨거에 토큰 생성기 포함 여부
)
