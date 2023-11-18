package kr.co.anna.domain.config

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle
import java.util.*

/**
 * JPA 가 실행하는 쿼리와 파라미터를 볼 수 있게 해주는 P6Spy 설정
 */
class P6SpyLogger : MessageFormattingStrategy {
    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String,
        prepared: String?,
        sql: String,
        url: String?
    ): String {
        val callStack = Stack<String>()
        val stackTrace = Throwable().stackTrace
        for (i in stackTrace.indices) {
            val trace = stackTrace[i].toString()
            if (trace.startsWith("kr.co.anna") && !trace.contains("P6SpyLogger") && !trace.contains("$$")) {
                callStack.push(trace)
            }
        }
        val callStackBuilder = StringBuilder()
        var order = 1
        while (callStack.size != 0) {
            callStackBuilder.append(
                """
		${order++}. ${callStack.pop()}"""
            )
        }
        val message = StringBuilder()
            .append("\n\tExecution Time: ").append(elapsed).append(" ms\n")
            .append("\n----------------------------------------------------------------------------------------------------")
            .toString()
        return sqlFormat(sql, category, message)
    }

    private fun sqlFormat(sql: String, category: String, message: String): String {
        var sql = sql
        if (sql.trim { it <= ' ' }.isEmpty()) {
            return ""
        }
        if (Category.STATEMENT.getName().equals(category)) {
            val s = sql.trim { it <= ' ' }.toLowerCase(Locale.ROOT)
            sql = if (s.startsWith("create") || s.startsWith("alter") || s.startsWith("comment")) {
                FormatStyle.DDL
                    .formatter
                    .format(sql)
            } else {
                FormatStyle.BASIC
                    .formatter
                    .format(sql)
            }
        }
        return StringBuilder().append("\n")
            .append(sql)
            .append(message)
            .toString()
    }
}
