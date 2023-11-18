package kr.co.anna.lib.security.jwt

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import kr.co.anna.domain.model.Role
import kr.co.anna.lib.security.SignInUser
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

/**
 * JWT(JSON Web Token)를 생성하는 책임을 가진 객체
 */
@Component
class JwtGenerator(
    private val jwtProperties: JwtProperties,

    ) {

    private val key: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.base64EncodedSecret))

    fun generateUserToken(signInUser: SignInUser): String {
        return Jwts.builder()
            .setSubject("kopring_graphql_querydsl")
            .claim(JwtKeys.UID.keyName, signInUser.username)
            .claim(JwtKeys.USER_OID.keyName, signInUser.userOid())
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(expiration(jwtProperties.swaggerTokenDurationHr))
            .compact()
    }


    private fun expiration(hour: Int?): Date = Date(Date().time + (1000 * 60 * 60 * hour!!.toInt()))
}
