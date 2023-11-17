package kr.co.anna.lib.error

import kr.co.anna.lib.utils.MessageUtil

class UnauthenticatedAccessException
    : RuntimeException(MessageUtil.getMessage("UNAUTHORIZED_ACCESS")) {
}
