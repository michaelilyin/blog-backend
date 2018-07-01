import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

subprojects {

  apply {
    plugin("java")
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-kapt")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
  }

  repositories {
    maven { setUrl("https://repo.spring.io/snapshot") }
    maven { setUrl("https://repo.spring.io/milestone") }
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
  }

  tasks.withType<Jar> {
    baseName = project.name
    version = "${project.version}"
    enabled = true
  }

}