package kr.co.anna.lib.utils

import org.springframework.context.support.MessageSourceAccessor

object MessageUtil {
    var messageSourceAccessor: MessageSourceAccessor? = null

    fun getMessage(key: String?): String {
        return messageSourceAccessor!!.getMessage(key!!)
    }
}
