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
