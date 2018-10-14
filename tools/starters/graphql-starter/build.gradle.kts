val kotlinVersion: String by extra
val kotlinLoggingVersion: String by extra
val graphqlStarterVersion: String by extra
val graphqlVersion: String by extra
val springBootVersion: String by extra

dependencies {
  compile(kotlin("stdlib-jdk8", kotlinVersion))
  compile(kotlin("reflect", kotlinVersion))
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")
  compile("org.springframework.boot:spring-boot-starter:$springBootVersion")
  compile("org.springframework.boot:spring-boot-configuration-processor:$springBootVersion")
  compile("com.graphql-java:graphiql-spring-boot-starter:$graphqlStarterVersion")
  compile("com.graphql-java:graphql-spring-boot-starter:$graphqlStarterVersion")
  compile("com.graphql-java:graphql-java-tools:$graphqlVersion")
}