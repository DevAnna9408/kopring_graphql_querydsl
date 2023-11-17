package kr.co.anna.lib.security

import kr.co.anna.domain.model.Role
import org.springframework.security.core.GrantedAuthority

/**
 * 로그인 한 사용자의 정보를 담는 객체
 * 이 객체는 Spring Security Context 에 저장되고,
 * 필요할 때 SecurityContextHolder.getContext().authentication.principal as? AuthUser 를 호출해서 얻들 수 있다.

 */
data class AuthUser(
    val userId: String,
    val userOid: Long,
    val authorities: Collection<GrantedAuthority>,
    val roles: List<Role>
) {
    constructor(signInUser: SignInUser) : this(
        signInUser.username,
        signInUser.userOid()!!,
        signInUser.authorities,
        signInUser.roles()
    )

    fun isFreePass(): Boolean {
        return authorities.map { it.authority }.contains(Role.ROLE_SYS_ADMIN.getCode())
    }
}

