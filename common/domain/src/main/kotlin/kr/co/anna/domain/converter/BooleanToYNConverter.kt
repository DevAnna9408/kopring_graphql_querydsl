package kr.co.anna.domain.converter

import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * DB data 'Y/N' 과 객체 boolean 컨버터
 * 글로벌 설정
 */
@Converter(autoApply = true)
class BooleanToYNConverter: AttributeConverter<Boolean, String> {

    override fun convertToDatabaseColumn(attribute: Boolean?): String {
        if (attribute == null) return "N"
        return if (attribute) "Y" else "N"
    }

    override fun convertToEntityAttribute(yn: String?): Boolean {
        return "Y".equals(yn, true)
    }
}
