import java.text.SimpleDateFormat
import java.util.*

buildscript {
  val kotlinVersion = "1.2.50"
  val springBootVersion = "2.0.3.RELEASE"

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

allprojects {
  group = "ru.michaelilyin"
  version = "0.0.1-SNAPSHOT"

  extra["kotlinVersion"] = "1.2.50"
  extra["springBootVersion"] = "2.0.3.RELEASE"
  extra["springCloudVersion"] = "Finchley.RELEASE"
  extra["springEurekaVersion"] = "1.4.4.RELEASE"
  extra["liquibaseVersion"] = "3.5.5"
  extra["graphqlVersion"] = "4.3.0"
  extra["graphqlStarterVersion"] = "4.0.0.M1"
  extra["kotlinLoggingVersion"] = "1.4.9"
  extra["springSecurityOAuthVersion"] = "2.3.2.RELEASE"

  repositories {
    mavenLocal()
    mavenCentral()
  }

  apply {
    plugin("eclipse")
    plugin("idea")

  }
}

tasks.withType<Wrapper> {
  gradleVersion = "4.7"
}