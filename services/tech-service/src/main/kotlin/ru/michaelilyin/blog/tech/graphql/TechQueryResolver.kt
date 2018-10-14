package ru.michaelilyin.blog.tech.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringBootVersion
import org.springframework.core.SpringVersion
import org.springframework.stereotype.Component
import ru.michaelilyin.boot.starter.graphql.dto.StatusInfo
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class TechQueryResolver @Autowired() constructor(
  @Value("\${build.version}") private val version: String,
  @Value("\${build.time}") private val time: String
) : GraphQLQueryResolver {
  val javaVersion = System.getProperty("java.version")
  val startDate = LocalDateTime.now()

  fun techServiceStatus(): StatusInfo = StatusInfo(
    name = "tech-service",
    version = version,
    buildTime = ZonedDateTime.parse(time).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime(),
    javaVersion = javaVersion,
    startTime = startDate,
    uptime = Duration.between(startDate, LocalDateTime.now()).toMillis(),
    springVersion = SpringVersion.getVersion() ?: "undefined",
    springBootVersion = SpringBootVersion.getVersion() ?: "undefined"
  )
}