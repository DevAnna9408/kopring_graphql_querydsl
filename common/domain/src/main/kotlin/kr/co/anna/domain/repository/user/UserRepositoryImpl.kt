package kr.co.anna.domain.repository.user

import com.querydsl.core.types.dsl.BooleanExpression
import kr.co.anna.domain._common.DomainMessageUtil
import kr.co.anna.domain.exception.DomainEntityNotFoundException
import kr.co.anna.domain.model.user.QUser
import kr.co.anna.domain.model.user.User
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class UserRepositoryImpl : QuerydslRepositorySupport(User::class.java), UserRepositoryCustom {

    private val qUser = QUser.user

    override fun getByOid(oid: Long): User {
        return from(qUser)
            .where(
                isActive(),
                qUser.oid.eq(oid)
            )
            .fetchOne() ?: throw DomainEntityNotFoundException(oid, User::class, DomainMessageUtil.getMessage("USER_NOT_FOUND"))
    }

    override fun getByUserId(userId: String): User {
        return from(qUser)
            .where(
                isActive(),
                qUser.userId.eq(userId)
            )
            .fetchOne() ?: throw DomainEntityNotFoundException(userId, User::class, DomainMessageUtil.getMessage("USER_NOT_FOUND"))
    }

    private fun isActive(): BooleanExpression? {
        return  qUser.status.eq(User.Status.ACTIVE)
    }
}
