package ru.michaelilyin.blog.welcome.mapper

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import ru.michaelilyin.blog.welcome.dto.CommonRecord
import ru.michaelilyin.blog.welcome.dto.InfoType
import java.sql.ResultSet

object CommonRecordMapper : RowMapper<CommonRecord> {
  override fun mapRow(rs: ResultSet, rowNum: Int) = CommonRecord(
    key = rs.getString("key"),
    content = rs.getString("content"),
    type = InfoType.valueOf(rs.getString("type"))
  )

  operator fun invoke(record: CommonRecord) =
    MapSqlParameterSource()
      .addValue("key", record.key)
      .addValue("content", record.content)
      .addValue("type", record.type.toString())
}