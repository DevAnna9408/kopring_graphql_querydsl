package kr.co.anna.domain.repository.user

import kr.co.anna.domain.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserRepositoryCustom {

    fun getByOid(oid: Long): User
    fun getByUserId(userId: String) : User

}
