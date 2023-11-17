package kr.co.anna.domain._common

import org.springframework.context.support.MessageSourceAccessor

object DomainMessageUtil {
    var messageSourceAccessorDomain: MessageSourceAccessor? = null


    fun getMessage(key: String?): String {
        println(messageSourceAccessorDomain)
        return messageSourceAccessorDomain!!.getMessage(key!!)
    }

    fun getMessage(key: String?, objs: Array<Any?>?): String {
        return messageSourceAccessorDomain!!.getMessage(key!!, objs)
    }
}
