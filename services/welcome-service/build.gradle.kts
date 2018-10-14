val kotlinVersion: String by extra
val kotlinLoggingVersion: String by extra
val springBootVersion: String by extra
val graphqlStarterVersion: String by extra
val graphqlVersion: String by extra
val springVersion: String by extra
val liquibaseVersion: String by extra

dependencies {
  compileOnly(project(":tools:starters:graphql-starter"))
  compileOnly(kotlin("stdlib-jdk8", kotlinVersion))
  compileOnly(kotlin("reflect", kotlinVersion))
  compileOnly("org.springframework:spring-web:$springVersion")
  compileOnly("org.springframework:spring-jdbc:$springVersion")
  compileOnly("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
  compileOnly("org.liquibase:liquibase-core:$liquibaseVersion")

//  testCompile("org.springframework.boot:spring-boot-starter-test")
//  testCompile("org.springframework.security:spring-security-test")
}