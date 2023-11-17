package kr.co.anna.api.dto.user

import kr.co.anna.domain.model.Role
import kr.co.anna.domain.model.user.User
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpIn(
    val userId: String,
    val name: String,
    val email: String,
    val password: String,
) {
    fun toEntity(passwordEncoder: PasswordEncoder): User {
        return User(
            userId = userId,
            name = name,
            email = email,
            roles = listOf(Role.ROLE_USER),
            password = passwordEncoder.encode(password),
        )
    }
}
