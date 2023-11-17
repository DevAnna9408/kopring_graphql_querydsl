package kr.co.anna.api.service.command.user

import kr.co.anna.api.dto.user.*
import kr.co.anna.domain.model.user.User
import kr.co.anna.domain.repository.user.UserRepository
import kr.co.anna.lib.security.jwt.JwtGenerator
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@Transactional
class UserCommandService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,

    @org.springframework.beans.factory.annotation .Value("\${front.url}")
    private val frontUrl: String,


    private val jwtGenerator: JwtGenerator,
) {
    fun createUser(signUpIn: SignUpIn): UserSimpleOut {
        val user: User = try {
            userRepository.save(signUpIn.toEntity(passwordEncoder))
        } catch (e: DataIntegrityViolationException) {
            // index 이름 및 DataIntegrityViolationException 구현에 의존하므로 좋은 방식은 아니지만,
            // userId, email 각각에 대해 쿼리를 2번 날릴 필요가 없는 장점이 있고, 핵심 비즈 로직도 아니므로 편리하게 적용
            val defaultMsg = "계정을 생성할 수 없습니다. 관리자에게 문의해주세요."
            val msg = e.message ?: throw RuntimeException(defaultMsg)
            when {
                msg.contains("USER_ID") -> throw IllegalArgumentException("이미 사용 중인 사용자 아이디 입니다.")
                msg.contains("EMAIL") -> throw IllegalArgumentException("이미 사용 중인 이메일 입니다.")
                else -> throw RuntimeException(defaultMsg)
            }
        }
        return UserSimpleOut.fromEntity(user)
    }

    fun saveUser(oid: Long, userUpdateIn: UserUpdateIn): UserSimpleOut {
        val dbUser = userRepository.getByOid(oid)
        dbUser.updateWith(
            User.NewValue(
                name = userUpdateIn.name,
                email = userUpdateIn.email,
                password = passwordEncoder.encode(userUpdateIn.password),
            )
        )
        return UserSimpleOut.fromEntity(dbUser)
    }

    fun changePassword(oid: Long, passwordIn: PasswordIn) {
        val dbUser = userRepository.getByOid(oid)
        dbUser.changePassword(passwordEncoder.encode(passwordIn.newPassword))
    }

    fun resetPassword(oid: Long): String {
        val dbUser = userRepository.getByOid(oid)
        val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val initPassword = (1..7)
            .map { charPool.random() }
            .joinToString("")
        dbUser.changePassword(passwordEncoder.encode(initPassword))
        return initPassword
    }

    /**
     * 비밀번호 찾기
     *
     * @param userId
     * @param email
     */
    fun findPassword(userId: String, email: String) {
        val user = userRepository.getByUserIdAndEmail(userId, email)
    }

    fun unlockUser(userId: String): UserOut {
        val dbUser = userRepository.getByUserId(userId)
        dbUser.unlock()
        return UserOut.fromEntity(dbUser)
    }


    fun isUseUserEmail(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    fun isUseUserId(userId: String): Boolean {
        return userRepository.findByUserId(userId).isPresent
    }

    @Transactional(readOnly = true)
    fun isUse(type: String, value: String?): Boolean? {
        if (type == "userId")
            return !isUseUserId(value!!)
        else if (type == "email")
            return !isUseUserEmail(value!!)
        return true
    }
}
