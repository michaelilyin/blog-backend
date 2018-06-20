import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.apache.tools.ant.filters.ReplaceTokens
import java.text.SimpleDateFormat
import java.util.*

group = "ru.michaelilyin"
version = "0.0.1-SNAPSHOT"

buildscript {
    var kotlinVersion: String by extra
    kotlinVersion = "1.2.21"
    var springBootVersion: String by extra
    springBootVersion = "2.0.1.RELEASE"

    repositories {
        mavenCentral()
        maven {
            setUrl("https://repo.spring.io/snapshot")
        }
        maven {
            setUrl("https://repo.spring.io/milestone")
        }
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath(kotlin("allopen", kotlinVersion))
    }
}

subprojects {

    project.extra["kotlinVersion"] = "1.2.21"
    project.extra["springBootVersion"] = "2.0.1.RELEASE"
    project.extra["liquibaseVersion"] = "3.5.5"
    project.extra["graphqlVersion"] = "4.3.0"
    project.extra["graphqlStarterVersion"] = "4.0.0.M1"
    project.extra["kotlinLoggingVersion"] = "1.4.9"
    project.extra["springSecurityOAuthVersion"] = "2.3.2.RELEASE"

    plugins {
        java
    }

    apply {
        plugin("kotlin")
        plugin("kotlin-spring")
        plugin("eclipse")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("kotlin-kapt")
    }

    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            setUrl("https://repo.spring.io/snapshot")
        }
        maven {
            setUrl("https://repo.spring.io/milestone")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
    }

    fun buildTime(): String {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
        df.timeZone = TimeZone.getTimeZone("UTC")
        return df.format(Date())
    }

    tasks.withType<ProcessResources> {
        val args = kotlin.collections.mapOf(kotlin.Pair("tokens", kotlin.collections.mapOf(
                kotlin.Pair("project.version", project.version),
                kotlin.Pair("project.build.time", buildTime())
        )))
        filter(args, ReplaceTokens::class.java)
    }
}