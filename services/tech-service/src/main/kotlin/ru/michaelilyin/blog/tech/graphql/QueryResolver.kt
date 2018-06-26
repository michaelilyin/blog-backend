package ru.michaelilyin.blog.tech.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.joda.time.DateTime
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.tech.dto.StatusInfo

@Component
class QueryResolver @Autowired() constructor(
  @Value("\${build.version}") private val version: String,
  @Value("\${build.time}") private val time: String
) : GraphQLQueryResolver {

  val javaVersion = System.getProperty("java.version")
  val startDate = DateTime.now()

  fun status(): StatusInfo = StatusInfo(version, time, javaVersion, DateTime.now().minus(startDate.millis).millis)
}