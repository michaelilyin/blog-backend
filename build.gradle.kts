import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.apache.tools.ant.filters.ReplaceTokens
import java.text.SimpleDateFormat
import java.util.*

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

group = "ru.michaelilyin"
version = "0.0.1-SNAPSHOT"

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

val kotlinVersion: String by extra

dependencies {
    compile("org.springframework.boot:spring-boot-starter-actuator")
	compile("org.springframework.boot:spring-boot-starter-aop")
	compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.boot:spring-boot-starter-web")
	compile("org.springframework.boot:spring-boot-starter-websocket")
	compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-devtools")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile(kotlin("stdlib-jdk8", kotlinVersion))
    compile(kotlin("reflect", kotlinVersion))
	compile("org.springframework.boot:spring-boot-devtools")
    compile("org.postgresql:postgresql")
    compile("org.liquibase:liquibase-core:3.5.5")
	compile("com.graphql-java:graphiql-spring-boot-starter:4.0.0.M1")
	compile("com.graphql-java:graphql-spring-boot-starter:4.0.0.M1")
    compile("com.graphql-java:graphql-java-tools:4.3.0")
    compile("io.github.microutils:kotlin-logging:1.4.9")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.springframework.security:spring-security-test")
}

tasks {
    "processResources"(ProcessResources::class) {
        val args = kotlin.collections.mapOf(kotlin.Pair("tokens", kotlin.collections.mapOf(
                kotlin.Pair("project.version", project.version),
                kotlin.Pair("project.build.time", buildTime())
        )))
        filter(args, org.apache.tools.ant.filters.ReplaceTokens::class.java)
    }
}

fun buildTime(): String {
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
    df.timeZone = TimeZone.getTimeZone("UTC")
    return df.format(Date())
}