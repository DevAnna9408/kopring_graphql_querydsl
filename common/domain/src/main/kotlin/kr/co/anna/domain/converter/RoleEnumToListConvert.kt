package kr.co.anna.domain.converter

import kr.co.anna.domain._common.EnumModel
import kr.co.anna.domain.model.Role
import javax.persistence.Convert

/**
 * role list num 변환
 */
@Convert
class RoleEnumToListConvert(private val targetEnumClass: Class<out EnumModel> = Role::class.java) :
    EnumToListConverter(targetEnumClass) {
}
