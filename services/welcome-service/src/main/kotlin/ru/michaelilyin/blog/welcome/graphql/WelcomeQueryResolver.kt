package ru.michaelilyin.blog.welcome.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringBootVersion
import org.springframework.core.SpringVersion
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.welcome.dto.CommonInfo
import ru.michaelilyin.blog.welcome.dto.CommonRecord
import ru.michaelilyin.blog.welcome.dto.InfoType
import ru.michaelilyin.blog.welcome.services.CommonInfoService
import ru.michaelilyin.boot.starter.graphql.dto.StatusInfo
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.activation.CommandInfo

@Component
class WelcomeQueryResolver @Autowired() constructor(
  @Value("\${build.version}") private val version: String,
  @Value("\${build.time}") private val time: String,
  private val commonInfoService: CommonInfoService
) : GraphQLQueryResolver {
  val javaVersion = System.getProperty("java.version")
  val startDate = LocalDateTime.now()

  fun welcomeServiceStatus() = StatusInfo(
    name = "welcome-service",
    version = version,
    buildTime = ZonedDateTime.parse(time).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime(),
    javaVersion = javaVersion,
    startTime = startDate,
    uptime = Duration.between(startDate, LocalDateTime.now()).toMillis(),
    springVersion = SpringVersion.getVersion() ?: "undefined",
    springBootVersion = SpringBootVersion.getVersion() ?: "undefined"
  )

  fun commonInfo() = CommonInfo()

  fun updateCommonInfo(records: List<CommonRecord>): List<CommonRecord> {
    return records.map { commonInfoService.update(it) }
  }
}