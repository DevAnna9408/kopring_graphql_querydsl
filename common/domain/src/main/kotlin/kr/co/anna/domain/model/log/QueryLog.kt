package kr.co.anna.domain.model.log

import kr.co.anna.domain._common.AbstractEntity
import javax.persistence.*

@Entity
@Table( name = "QUERY_LOG")
class QueryLog(
    oid: Long? = null,

    @Column(name="URL")
    val url: String,

    @Column(name="USER_ID")
    val userId: String,

    @Column(name="ACTION")
    @Enumerated(EnumType.STRING)
    val action: Action,
) : kr.co.anna.domain._common.AbstractEntity(oid) {
    enum class Action {
        QUERY, DOWNLOAD
    }
}
