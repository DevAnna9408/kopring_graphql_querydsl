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
) {
    fun createUser(signUpIn: SignUpIn): UserOut {
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
        return UserOut.fromEntity(user)
    }

}
