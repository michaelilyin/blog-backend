package ru.michaelilyin.blog.model.mapper

import org.springframework.jdbc.core.RowMapper
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.model.AuditSetting
import java.sql.ResultSet

object AuditSettingJdbcMapper: RowMapper<AuditSetting> {
    override fun mapRow(rs: ResultSet, rowNum: Int): AuditSetting? {
        val severityStr = rs.getString("severity")
        val severity = if (severityStr != null) AuditLevel.valueOf(severityStr) else null
        return AuditSetting(
                id = rs.getLong("id"),
                tag = rs.getString("tag"),
                login = rs.getString("login"),
                trace = rs.getBoolean("trace"),
                severity = severity
        )
    }
}