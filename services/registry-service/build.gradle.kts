val kotlinVersion: String by extra
val kotlinLoggingVersion: String by extra

dependencies {
  compile(kotlin("stdlib-jdk8", kotlinVersion))
  compile(kotlin("reflect", kotlinVersion))
  compile("org.springframework.boot:spring-boot-starter-actuator")
  compile("org.springframework.boot:spring-boot-starter-aop")
  compile("org.springframework.boot:spring-boot-starter-security")
  compile("org.springframework.boot:spring-boot-starter-web")
  compile("org.springframework.boot:spring-boot-starter-websocket")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")
  compile("org.springframework.boot:spring-boot-devtools")
  compile("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

  testCompile("org.springframework.boot:spring-boot-starter-test")
  testCompile("org.springframework.security:spring-security-test")
}