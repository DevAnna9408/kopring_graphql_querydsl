package kr.co.anna.domain.exception

class AlreadyAssignedException(
    msg: String,
    val userOid: Long,
    val userName: String,
) : RuntimeException(msg) {
}
