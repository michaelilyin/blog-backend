val kotlinVersion: String by extra
val liquibaseVersion: String by extra
val graphqlVersion: String by extra
val graphqlStarterVersion: String by extra
val kotlinLoggingVersion: String by extra
val springSecurityOAuthVersion: String by extra
val springEurekaVersion: String by extra
val springCloudVersion: String by extra

dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
  }
}

dependencies {
  compile(kotlin("stdlib-jdk8", kotlinVersion))
  compile(kotlin("reflect", kotlinVersion))
  compile("org.springframework.boot:spring-boot-starter-actuator")
  compile("org.springframework.boot:spring-boot-starter-aop")
//  compile("org.springframework.boot:spring-boot-starter-security")
  compile("org.springframework.boot:spring-boot-starter-web")
//  compile("org.springframework.boot:spring-boot-starter-websocket")
//  compile("org.springframework.boot:spring-boot-starter-data-jpa")
//  compile("org.springframework.security.oauth:spring-security-oauth2:$springSecurityOAuthVersion")
  compile("org.springframework.cloud:spring-cloud-starter-eureka:$springEurekaVersion")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")
//  compile("org.postgresql:postgresql")
//  compile("org.liquibase:liquibase-core:$liquibaseVersion")
  compile("com.graphql-java:graphiql-spring-boot-starter:$graphqlStarterVersion")
  compile("com.graphql-java:graphql-spring-boot-starter:$graphqlStarterVersion")
  compile("com.graphql-java:graphql-java-tools:$graphqlVersion")
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

  testCompile("org.springframework.boot:spring-boot-starter-test")
//  testCompile("org.springframework.security:spring-security-test")
}