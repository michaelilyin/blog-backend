package ru.michaelilyin.blog.model

import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.RowMapper
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import java.sql.ResultSet
import java.time.LocalDateTime

data class AuditRecord(
        var id: Long,
        var tag: String,
        var thread: String,
        var time: LocalDateTime,
        var severity: AuditLevel,
        var login: String,
        var message: String,
        var trace: String?
)

object AuditRecordMapper: RowMapper<AuditRecord> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AuditRecord? {
        return AuditRecord(
                id = rs.getLong("id"),
                tag = rs.getString("tag"),
                thread = rs.getString("thread"),
                time = LocalDateTime.from(rs.getDate("time").toInstant()),
                severity = AuditLevel.valueOf(rs.getString("severity")),
                login = rs.getString("login"),
                message = rs.getString("message"),
                trace = rs.getString("trace")
        )
    }
}