package kr.co.anna.lib.error

import kr.co.anna.lib.utils.MessageUtil

class JwtUnauthenticatedAccessException
    : RuntimeException(MessageUtil.getMessage("UNAUTHORIZED_ACCESS")) {
}
