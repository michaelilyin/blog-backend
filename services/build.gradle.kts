import org.apache.tools.ant.filters.ReplaceTokens
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar
import java.text.SimpleDateFormat
import java.util.*
import com.palantir.gradle.docker.DockerExtension

subprojects {

  apply {
    plugin("java")
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-kapt")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
    plugin("com.palantir.docker")
  }

  repositories {
    maven { setUrl("https://repo.spring.io/snapshot") }
    maven { setUrl("https://repo.spring.io/milestone") }
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
  }

  tasks.withType<ProcessResources> {
    val args = mapOf(
      Pair("tokens", mapOf(
        Pair("project.version", project.version),
        Pair("project.build.time", buildTime())
      ))
    )
    filter(args, ReplaceTokens::class.java)
  }

  configure<DockerExtension> {
    val build by tasks
    dependsOn(build)

    val bootJar: BootJar by tasks

    pull(false)
    files(bootJar.archivePath)
    name = "${project.group}/${bootJar.baseName}"
    buildArgs(mapOf(Pair("JAR_FILE", bootJar.archiveName)))
    tags("latest", bootJar.version)
  }
}

fun buildTime(): String {
  val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
  df.timeZone = TimeZone.getTimeZone("UTC")
  return df.format(Date())
}