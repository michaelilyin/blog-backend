allprojects {
  group = "ru.michaelilyin"
  version = "0.0.1-SNAPSHOT"

  extra["kotlinVersion"] = "1.2.21"
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
  }
}
