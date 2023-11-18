package kr.co.anna.domain._common

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*


@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OID")
    val oid: Long?
) {


    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false, columnDefinition = "DATETIME")
    protected lateinit var createdTime: LocalDateTime


    @Column(name = "CREATED_BY", updatable = false)
    @CreatedBy
    protected var createdBy: String? = null

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE",columnDefinition = "DATETIME")
    protected lateinit var lastModifiedTime: LocalDateTime

    @Column(name = "LAST_MODIFIED_BY", updatable = true)
    @LastModifiedBy
    protected var lastModifiedBy: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractEntity

        if (oid != other.oid) return false

        return true
    }

    override fun hashCode(): Int {
        return oid?.hashCode() ?: 0
    }
}
