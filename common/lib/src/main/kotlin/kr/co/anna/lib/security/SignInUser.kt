package kr.co.anna.lib.security

import kr.co.anna.domain.model.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SignInUser(
    val user: User,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return user.role().map {  SimpleGrantedAuthority(it.getCode()) }.toMutableSet()
    }

    override fun getPassword(): String {
        return user.password()
    }

    override fun getUsername(): String {
        return user.userId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return !user.locked()
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return user.checkActiveUser()
    }

    fun name() = user.name()
    fun email() = user.email()
    fun userOid() = user.oid
    fun roles() = user.role()
    fun status() = user.status()

}
