package kr.co.anna.api.dto.user

import kr.co.anna.domain.model.user.User

data class UserSimpleOut(
    val userId: String,
    val name: String,
    val email: String,
) {
    companion object {
        fun fromEntity(e: User): UserSimpleOut {
            return UserSimpleOut(
                userId = e.userId,
                name = e.name(),
                email = e.email(),
            )
        }
    }
}
