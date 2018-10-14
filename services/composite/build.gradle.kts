val kotlinVersion: String by extra
val kotlinLoggingVersion: String by extra
val springBootVersion: String by extra
val graphqlStarterVersion: String by extra
val graphqlVersion: String by extra
val embeddedPostgresVersion: String by extra
val liquibaseVersion: String by extra

apply {
  plugin("org.springframework.boot")
}

dependencies {
  compile(kotlin("stdlib-jdk8", kotlinVersion))
  compile(kotlin("reflect", kotlinVersion))
  compile(project(":services:tech-service"))
  compile(project(":services:welcome-service"))
  compile(project(":tools:starters:graphql-starter"))
  compile("org.springframework.boot:spring-boot-starter-web")
  compile("org.springframework.boot:spring-boot-starter-actuator")
  compile("org.springframework.boot:spring-boot-starter-jdbc")
  compile("org.postgresql:postgresql")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
  compile("org.liquibase:liquibase-core:$liquibaseVersion")

  testCompile("org.springframework.boot:spring-boot-starter-test")
//  testCompile("org.springframework.security:spring-security-test")
}