package ru.michaelilyin.blog.dao.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.namedparam.set
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.michaelilyin.blog.dao.AuditRepository
import ru.michaelilyin.blog.model.AuditRecord
import ru.michaelilyin.blog.model.AuditRecordMapper
import java.sql.Types

@Repository
class JdbcAuditRepository @Autowired() constructor(
        private val namedJdbcTemplate: NamedParameterJdbcTemplate
) : AuditRepository {

    // language=PostgreSQL
    private val CREATE_AUDIT = """INSERT INTO bl_audit (
  tag, thread, time, severity, login, message, trace
) VALUES (
    :tag, :thread, :time, :severity, :login, :message, :trace::JSONB
)"""

    // language=PostgreSQL
    private val GET_AUDIT_PAGE = """SELECT *
FROM bl_audit
ORDER BY time DESC
OFFSET :offset
LIMIT :limit"""

    override fun createAuditRecord(record: AuditRecord): AuditRecord {
        val params = mutableMapOf<String, Any?>()

        params["id"] = record.id
        params["tag"] = record.tag
        params["thread"] = record.thread
        params["time"] = record.time
        params["severity"] = record.severity.name
        params["login"] = record.login
        params["message"] = record.message
        val trace = record.trace
        params["trace"] = trace

        val keyHolder = GeneratedKeyHolder()
        namedJdbcTemplate.update(CREATE_AUDIT, MapSqlParameterSource(params), keyHolder, arrayOf("id"))
        record.id = keyHolder.key!!.toLong()
        return record
    }

    override fun getAuditRecords(offset: Long, limit: Long): List<AuditRecord> {
        val params = MapSqlParameterSource()

        params["offset"] = offset
        params["limit"] = limit

        return namedJdbcTemplate.query(GET_AUDIT_PAGE, params, AuditRecordMapper)
    }
}