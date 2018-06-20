val kotlinVersion: String by extra
val liquibaseVersion: String by extra
val graphqlVersion: String by extra
val graphqlStarterVersion: String by extra
val kotlinLoggingVersion: String by extra

dependencies {
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-starter-aop")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-websocket")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")
    compile(kotlin("stdlib-jdk8", kotlinVersion))
    compile(kotlin("reflect", kotlinVersion))
    compile("org.springframework.boot:spring-boot-devtools")
    compile("org.postgresql:postgresql")
    compile("org.liquibase:liquibase-core:$liquibaseVersion")
    compile("com.graphql-java:graphiql-spring-boot-starter:$graphqlStarterVersion")
    compile("com.graphql-java:graphql-spring-boot-starter:$graphqlStarterVersion")
    compile("com.graphql-java:graphql-java-tools:$graphqlVersion")
    compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.springframework.security:spring-security-test")
}