package kr.co.anna.api.dto.user

data class UserUpdateIn(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null
) {
}
