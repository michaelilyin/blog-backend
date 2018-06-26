val kotlinVersion: String by extra
val kotlinLoggingVersion: String by extra
val springBootVersion: String by extra
val springCloudVersion: String by extra
val springEurekaVersion: String by extra
val graphqlStarterVersion: String by extra
val graphqlVersion: String by extra

dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
  }
}

dependencies {
  compile(kotlin("stdlib-jdk8", kotlinVersion))
  compile(kotlin("reflect", kotlinVersion))
  compile("org.springframework.boot:spring-boot-starter-web")
  compile("org.springframework.boot:spring-boot-starter-actuator")
  compile("org.springframework.cloud:spring-cloud-starter-eureka:$springEurekaVersion")
//  compile("org.springframework.cloud:spring-cloud-starter-config")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
  compile("com.graphql-java:graphiql-spring-boot-starter:$graphqlStarterVersion")
  compile("com.graphql-java:graphql-spring-boot-starter:$graphqlStarterVersion")
  compile("com.graphql-java:graphql-java-tools:$graphqlVersion")

  testCompile("org.springframework.boot:spring-boot-starter-test")
//  testCompile("org.springframework.security:spring-security-test")
}