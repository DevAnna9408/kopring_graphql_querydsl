package kr.co.anna.domain.exception

import kr.co.anna.domain._common.AbstractEntity
import kotlin.reflect.KClass

class DomainEntityNotFoundException(
    val id: Any,
    val entityClazz: KClass<out kr.co.anna.domain._common.AbstractEntity>,
    msg: String,
) : RuntimeException(msg) {
}
