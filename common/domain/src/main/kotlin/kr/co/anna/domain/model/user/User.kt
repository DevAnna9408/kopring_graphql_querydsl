package kr.co.anna.domain.model.user

import kr.co.anna.domain._common.AbstractEntity
import kr.co.anna.domain._common.EnumModel
import kr.co.anna.domain.converter.RoleEnumToListConvert
import kr.co.anna.domain.model.Role
import javax.persistence.*

/**
 * 회원
 */
@Entity
@Table(
    name = "USER",
    indexes = [
        Index(name = "IDX_USER__USER_ID", columnList = "USER_ID", unique = true),
        Index(name = "IDX_USER__EMAIL", columnList = "EMAIL", unique = true),
    ]
)
class User(
    oid: Long? = null, //pk

    @Column(name = "USER_ID")
    private val userId: String,

    @Column(name = "NAME")
    private var name: String,

    @Column(name = "EMAIL")
    private var email: String,

    @Column(name = "PASSWORD")
    private var password: String,

    @Column(name = "ROLES")
    @Convert(converter = RoleEnumToListConvert::class)
    private var roles: List<Role>,

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private var status: Status = Status.ACTIVE, // 사용자 상태 코드

) : kr.co.anna.domain._common.AbstractEntity(oid) {

    fun name() = name
    fun email() = email
    fun password() = password
    fun status() = status
    fun role() = roles
    fun userId() = userId

    enum class Status(private val korName: String, private val engName: String) : EnumModel {

        ACTIVE("활성", "ACTIVE"),
        WITHDRAW("탈퇴", "WITHDRAW"),
        INACTIVE("비활성", "INACTIVE");

        override fun getKorName(): String {
            return korName
        }

        override fun getEngName(): String {
            return engName
        }

        override fun getCode(): String {
            return name
        }
    }

    fun changePassword(password: String) {
        this.password = password
    }

    fun updateWith(n: NewValue) {
        if (!n.name.isNullOrBlank()) this.name = n.name
        if (!n.email.isNullOrBlank()) this.email = n.email
        if (!n.password.isNullOrBlank()) this.password = n.password
        this.status = n.status
        this.roles = n.roles

    }

    data class NewValue(
        val name: String? = null,
        val email: String? = null,
        val password: String? = null,
        val roles: MutableList<Role> = mutableListOf(Role.ROLE_USER),
        var status: Status = Status.ACTIVE,
        val failCnt: Int = 0,
        val locked: Boolean = false
    )

    fun checkActiveUser(): Boolean {
        return status().equals(Status.ACTIVE)
    }

}
