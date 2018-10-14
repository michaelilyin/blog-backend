package ru.michaelilyin.blog.welcome.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import ru.michaelilyin.blog.welcome.annotations.datasource.WelcomeJdbc
import ru.michaelilyin.blog.welcome.annotations.datasource.WelcomeNamedJdbc
import ru.michaelilyin.blog.welcome.dto.CommonRecord
import ru.michaelilyin.blog.welcome.mapper.CommonRecordMapper

@Repository
class CommonInfoRepository @Autowired() constructor(
  @WelcomeJdbc private val jdbcTemplate: JdbcTemplate,
  @WelcomeNamedJdbc private val namedJdbcTemplate: NamedParameterJdbcTemplate
) {
  //language=PostgreSQL
  private val findQuery = """SELECT *
FROM common_info
WHERE key = ?"""

  //language=PostgreSQL
  private val updateQuery = """UPDATE common_info
SET type    = type,
    content = content"""

  fun find(key: String): CommonRecord {
    return jdbcTemplate.queryForObject<CommonRecord>(findQuery, CommonRecordMapper, key)!!
  }

  fun update(record: CommonRecord) {
    namedJdbcTemplate.update(updateQuery, CommonRecordMapper(record))
  }
}