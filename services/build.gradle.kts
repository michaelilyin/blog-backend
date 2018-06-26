import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*

subprojects {

  apply {
    plugin("java")
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-kapt")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
  }

  repositories {
    maven { setUrl("https://repo.spring.io/snapshot") }
    maven { setUrl("https://repo.spring.io/milestone") }
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
  }

  tasks.withType<ProcessResources> {
    val args = mapOf(
      Pair("tokens", mapOf(
        Pair("project.version", project.version),
        Pair("project.build.time", buildTime())
      ))
    )
    filter(args, ReplaceTokens::class.java)
  }
}

fun buildTime(): String {
  val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
  df.timeZone = TimeZone.getTimeZone("UTC")
  return df.format(Date())
}