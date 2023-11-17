package kr.co.anna.lib.error

import org.springframework.validation.BindingResult

/**
 * 밸리데이션 에러처리
 */
class InvalidException(
    message: String? = null,
    val errors: BindingResult,
) : RuntimeException(
    message
) {
}
