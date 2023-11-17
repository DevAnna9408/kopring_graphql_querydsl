package kr.co.anna.lib.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import kr.co.anna.lib.error.JwtUnauthenticatedAccessException
import kr.co.anna.lib.security.AuthUser
import kr.co.anna.lib.security.SignInUser
import kr.co.anna.lib.security.UserDetailsServiceImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component

/**
 * JWT 문자열을 처리하는 책임을 가진 객체
 * JWT 토큰에서 인증 정보 조회
 */
@Component
class JwtProcessor(
    private val jwtProperties: JwtProperties,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {
    /**
     * jwt 문자열을 파싱하고 사용자 정보를 UsernamePasswordAuthenticationToken 에 담아 반환
     * UsernamePasswordAuthenticationToken 는 JwtFilter를 통해 Spring Security Context 에 저장되고,
     * SecurityContextHolder.getContext().authentication 로 꺼내서 사용 가능
     */
    fun extractAuthentication(jwt: String): UsernamePasswordAuthenticationToken {

        val jws = parse(jwt)
        val body = jws.body
        val userId = body[JwtKeys.UID.keyName].toString()
        val userOid = (body[JwtKeys.USER_OID.keyName] as Int).toLong()

        val userDetails = userDetailsServiceImpl.loadUserByUsername(userId) as SignInUser //db에서 id확인
        if (userDetails.userOid() != userOid) throw JwtUnauthenticatedAccessException()
        return UsernamePasswordAuthenticationToken(AuthUser(userDetails), jws, userDetails.authorities)

    }

    private fun parse(token: String): Jws<Claims> {

        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.base64EncodedSecret)))
            .build()
            .parseClaimsJws(token)

    }

}
