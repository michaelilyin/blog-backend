package ru.michaelilyin.blog.dao.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.michaelilyin.blog.annotations.cache.repository.CacheableRepository
import ru.michaelilyin.blog.annotations.cache.repository.InvalidateCache
import ru.michaelilyin.blog.dao.AuditSettingsRepository
import ru.michaelilyin.blog.model.AuditSetting
import ru.michaelilyin.blog.model.mapper.AuditRecordJdbcMapper
import ru.michaelilyin.blog.model.mapper.AuditSettingJdbcMapper

@CacheableRepository
class JdbcAuditSettingsRepository @Autowired() constructor(
        private val namedJdbcTemplate: NamedParameterJdbcTemplate
) : AuditSettingsRepository {

    // language=PostgreSQL
    private val SETTINGS_RQUEST = """WITH sev AS (
    SELECT severity
    FROM bl_audit_settings
    WHERE (login IS NULL OR login = :login)
          OR (tag IS NULL OR tag = :tag)
    ORDER BY tag NULLS LAST, login NULLS LAST
    LIMIT 1
)
SELECT
  -1                              AS id,
  :tag                            AS tag,
  :login                          AS login,
  coalesce(sev.severity, 'INFO')  AS severity,
  coalesce(bool_or(trace), FALSE) AS trace
FROM bl_audit_settings, sev
WHERE (login IS NULL OR login = :login)
      OR (tag IS NULL OR tag = :tag)
GROUP BY sev.severity
ORDER BY tag NULLS FIRST, login NULLS FIRST
"""

    override fun getSettingsFor(tag: String, login: String): AuditSetting {
        val params = mutableMapOf<String, Any>()
        params["login"] = login
        params["tag"] = tag

        val res = namedJdbcTemplate.query(SETTINGS_RQUEST, MapSqlParameterSource(params), AuditSettingJdbcMapper)
        return res[0]
    }

    @InvalidateCache
    fun createSettings() {

    }

    @InvalidateCache
    fun updateSettings() {

    }

    @InvalidateCache
    fun deleteSettings() {

    }
}