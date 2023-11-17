package kr.co.anna.domain.repository.log

import kr.co.anna.domain.model.log.QueryLog
import org.springframework.data.jpa.repository.JpaRepository

interface QueryLogRepository : JpaRepository<QueryLog, Long> {
}
