package kr.co.anna.api.service.query.base

import kr.co.anna.api.dto.user.UserOut
import kr.co.anna.domain.repository.user.UserRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class CommonInfoQueryService(
    private val userRepository: UserRepository
) {

    @Cacheable(cacheNames = ["users"])
    fun searchMembers(
        name: String?,
        pageable: Pageable
    ): Page<UserOut> {
        return userRepository.searchUsers( name, pageable)
            .map { UserOut.fromEntity(it) }
    }

}
