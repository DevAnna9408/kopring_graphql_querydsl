package kr.co.anna.domain.converter

import kr.co.anna.domain._common.EnumModel
import javax.persistence.AttributeConverter

/**
 * 콤마로 구분된 데이터들을 객체에서 컬렉션으로 변환
 */
open class EnumToListConverter(private var targetEnumClass: Class<out EnumModel>) :
    AttributeConverter<List<EnumModel>, String> {

    override fun convertToDatabaseColumn(attribute: List<EnumModel>?): String {
        if (attribute != null) {
            return attribute.joinToString(COMMA) { it.getCode() }
        }
        return ""
    }

    override fun convertToEntityAttribute(dbData: String?): List<EnumModel>? {
        if (!dbData.isNullOrBlank()) {
            return dbData.split(COMMA).toTypedArray()
                .map { att -> targetEnumClass.enumConstants.find { att == it.getCode() }!! }
        }
        return emptyList()

    }
    companion object {
        private const val COMMA: String = ","
    }
}
