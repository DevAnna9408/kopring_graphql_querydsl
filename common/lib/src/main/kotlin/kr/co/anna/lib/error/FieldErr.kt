package kr.co.anna.lib.error

import org.springframework.validation.BindingResult

class FieldErr(
    val field: String,
    val value: String?,
    val reason: String?) {

    companion object {
        internal fun from(bindingResult: BindingResult): List<FieldErr> {
            return bindingResult.fieldErrors
                .map { FieldErr(
                    it.field,
                    it.rejectedValue?.toString(),
                    it.defaultMessage
                ) }
        }
    }
}
