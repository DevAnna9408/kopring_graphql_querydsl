package kr.co.anna.domain.repository.user

import kr.co.anna.domain.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserRepositoryCustom {

    fun getByOid(oid: Long): User
    fun getByUserId(userId: String) : User
    fun getByEmail(email: String) : User
    fun getByUserIdAndEmail(userId: String, email: String): User
    fun searchUsers(
        name: String?,
        pageable: Pageable
    ): Page<User>
    fun findByUserId(userId: String): Optional<User>
    fun findByEmail(email: String): Optional<User>

}
