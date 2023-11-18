package kr.co.anna.lib.security

import kr.co.anna.domain.model.Role
import kr.co.anna.lib.utils.MessageUtil
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtil {

    private fun authUser(): AuthUser {
        val authUser = SecurityContextHolder.getContext().authentication.principal as? AuthUser
            ?: throw RuntimeException(MessageUtil.getMessage("UNAUTHENTICATED_USER"))
        return authUser
    }


    fun checkUserOid(userOid: Long) {
        val authUser = authUser()
        if (authUser.isFreePass()) return
        if (authUser.userOid != userOid) {
            throw AccessDeniedException(MessageUtil.getMessage("UNAUTHORIZED_ACCESS"))
        }
    }

    fun checkUserId(userId: String) {
        val authUser = authUser()
        if (authUser.isFreePass()) return
        if (authUser.userId != userId) {
            throw AccessDeniedException(MessageUtil.getMessage("UNAUTHORIZED_ACCESS"))
        }
    }

    fun checkManagerRole() {
        val authUser = authUser()
        if (authUser.isFreePass()) return
        if (!authUser.roles.any { it.getCode().equals(Role.ROLE_MANAGER.getCode()) }) {
            throw AccessDeniedException(MessageUtil.getMessage("ROLE_NOT_FOUND"))
        }
    }
}
