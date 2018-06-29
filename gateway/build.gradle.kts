import com.moowork.gradle.node.NodeExtension
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.variant.VariantBuilder
import com.palantir.gradle.docker.DockerExtension
import org.apache.tools.ant.taskdefs.condition.Os

plugins {
  id("com.moowork.node") version "1.2.0"
}

apply {
  plugin("com.palantir.docker")
}

node {
  download = true
}

val node = VariantBuilder(NodeExtension.get(project)).build().nodeExec

task<NpmTask>("clean") {
  dependsOn("npmInstall")
  setArgs(listOf("run", "clean"))
}

configure<DockerExtension> {
  pull(false)
  files(project.projectDir)
  name = "${project.group}/gateway"
  tags("latest", project.version as String)
}