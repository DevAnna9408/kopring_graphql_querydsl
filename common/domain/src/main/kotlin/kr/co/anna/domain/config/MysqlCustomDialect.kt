package kr.co.anna.domain.config

import org.hibernate.dialect.MySQL57Dialect

/**
 * 쿼리dsl에서 sql함수사용 설정
 */
class MysqlCustomDialect : MySQL57Dialect() {
    init {
       // registerFunction("REPLACE", StandardSQLFunction("REPLACE", StandardBasicTypes.STRING))
    }
}
