package ru.michaelilyin.boot.starter.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootVersion
import org.springframework.core.SpringProperties
import org.springframework.core.SpringVersion
import org.springframework.stereotype.Component
import ru.michaelilyin.boot.starter.graphql.dto.StatusInfo
import java.time.LocalDateTime

@Component
class QueryResolver @Autowired() constructor(
  private val properties: GraphQLProperties
) : GraphQLQueryResolver {

  private val javaVersion = System.getProperty("java.version")
  private val start = System.currentTimeMillis()
  private val startTime = LocalDateTime.now()

  fun status(): StatusInfo = StatusInfo(
    version = properties.version,
    buildTime = properties.buildTime,
    javaVersion = javaVersion,
    startTime = startTime,
    uptime = System.currentTimeMillis() - start,
    springVersion = SpringVersion.getVersion() ?: "undefined",
    springBootVersion = SpringBootVersion.getVersion() ?: "undefined"
  )
}