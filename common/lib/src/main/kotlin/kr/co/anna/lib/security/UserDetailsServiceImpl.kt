package kr.co.anna.lib.security

import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(userId: String?): UserDetails {
        if (userId.isNullOrEmpty()) throw IllegalArgumentException("로그인 아이디가 비어있습니다.")
        val user = userRepository.getByUserId(userId)
        return SignInUser(user)
    }
}


