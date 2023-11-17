package kr.co.anna.lib.error

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.validation.BindingResult
import java.time.ZoneId
import java.time.ZonedDateTime

class ErrorBody(
    val message: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val requestUrl: String? = null,
    val timestamp: ZonedDateTime,


    @JsonInclude(JsonInclude.Include.NON_NULL)
    val exceptionName: String? = null,

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val errors: List<FieldErr>? = null,

    ) {

    constructor(message: String, requestUrl: String?, exceptionName: String?) :
            this(message, requestUrl, ZonedDateTime.now(ZoneId.of("Z")), exceptionName)

    constructor(message: String, requestUrl: String?, exceptionName: String?, errors: BindingResult) :
            this(message, requestUrl, ZonedDateTime.now(ZoneId.of("Z")), exceptionName, FieldErr.from(errors))


}
