package kr.co.anna.domain.repository.user

import kr.co.anna.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
}
