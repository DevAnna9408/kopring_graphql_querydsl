package kr.co.anna.api.dto.user

import kr.co.anna.domain._common.EnumValue
import kr.co.anna.lib.security.SignInUser

data class SignInOut(
    val userId: String,
    val accessToken: String,
    val roles: List<EnumValue>,
    val customInfo: CustomInfo,
) {
    data class CustomInfo(
        val userName: String?,
        val email: String?,
        val userOid: Long?
    )

    companion object {
        fun from(signInUser: SignInUser, accessToken: String): SignInOut {
            return SignInOut(
                userId = signInUser.username,
                accessToken = accessToken,
                roles = signInUser.roles().map { EnumValue(it) },
                customInfo = CustomInfo(
                    userName = signInUser.name(),
                    email = signInUser.email(),
                    userOid = signInUser.userOid()
                )
            )
        }
    }
}
