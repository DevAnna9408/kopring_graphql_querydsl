package kr.co.anna.domain._common

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringExpression
import com.querydsl.core.types.dsl.StringPath

/**
 * 쿼리dsl에서 자주사용하는 함수
 */
fun removeBlank(name: StringPath): StringExpression {
    return Expressions.stringTemplate("replace({0},' ','')", name)
}

fun removeBlank(name: String): String {
    return name.replace("(\r\n|\r|\n|\n\r|\\p{Z}|\\t)".toRegex(), "")
}

fun containsKorEngName(inputName: String?, korName: StringPath, engName: StringPath): BooleanExpression? {
    if (inputName.isNullOrBlank()) {
        return null
    }
    val reInputName: String = removeBlank(inputName)
    return removeBlank(korName).contains(reInputName).or(removeBlank(engName).containsIgnoreCase(reInputName))
}

fun containsName(inputName: String?, name: StringPath): BooleanExpression? {
    return if (inputName.isNullOrBlank()) null
    else removeBlank(name).containsIgnoreCase(removeBlank(inputName))
}
