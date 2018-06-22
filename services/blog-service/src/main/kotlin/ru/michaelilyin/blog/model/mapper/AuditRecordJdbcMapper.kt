package ru.michaelilyin.blog.model.mapper

import org.springframework.jdbc.core.RowMapper
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.model.AuditRecord
import java.sql.ResultSet

object AuditRecordJdbcMapper: RowMapper<AuditRecord> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AuditRecord? {
        return AuditRecord(
                id = rs.getLong("id"),
                tag = rs.getString("tag"),
                thread = rs.getString("thread"),
                time = rs.getTimestamp("time").toLocalDateTime(),
                severity = AuditLevel.valueOf(rs.getString("severity")),
                login = rs.getString("login"),
                message = rs.getString("message"),
                trace = rs.getString("trace")
        )
    }
}