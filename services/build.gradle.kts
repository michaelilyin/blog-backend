import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.*

buildscript {
  val kotlinVersion: String by extra
  val springBootVersion: String by extra

  repositories {
    mavenCentral()
    maven { setUrl("https://repo.spring.io/snapshot") }
    maven { setUrl("https://repo.spring.io/milestone") }
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlinVersion))
    classpath(kotlin("allopen", kotlinVersion))
    classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
  }
}

subprojects {

  plugins {
    java
  }

  apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
    plugin("kotlin-kapt")
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