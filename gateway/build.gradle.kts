import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.frontend.KotlinFrontendExtension
import org.jetbrains.kotlin.gradle.frontend.npm.NpmExtension
import org.jetbrains.kotlin.gradle.frontend.util.withTask
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackBundleTask
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackRunTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

buildscript {
  val kotlinVersion: String by extra

  repositories {
    mavenCentral()
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
  }
  dependencies {
    classpath(kotlin("gradle-plugin", kotlinVersion))
    classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.31")
  }
}

repositories {
  mavenCentral()
}

val kotlinVersion: String by extra

plugins {
  java
}

apply {
  plugin("kotlin2js")
  plugin("org.jetbrains.kotlin.frontend")
}

dependencies {
  compile(kotlin("stdlib-js", kotlinVersion))
}

tasks.withType<Kotlin2JsCompile> {
  kotlinOptions.moduleKind = "commonjs"
  kotlinOptions.target = "v5"
  kotlinOptions.sourceMap = true

  configure<KotlinProjectExtension> {
    experimental.coroutines = Coroutines.ENABLE
  }
}



configure<KotlinFrontendExtension> {
  downloadNodeJsVersion = "latest"

  configure<NpmExtension> {
    dependency("@types/cors", "^2.8.4")
    dependency("@types/express", "^4.11.1")
    dependency("@types/graphql", "^0.12.5")
    dependency("@types/node-fetch", "^1.6.9")
    dependency("apollo-link-http", "^1.5.4")
    dependency("apollo-server-express", "^1.3.6")
    dependency("body-parser", "^1.18.2")
    dependency("cors", "^2.8.4")
    dependency("express", "^4.16.3")
    dependency("graphiql", "^0.11.11")
    dependency("graphql", "^0.13.2")
    dependency("graphql-tools", "^3.0.0")
    dependency("node-fetch", "^2.1.2")
    dependency("requirejs", "*")

    devDependency("ts2kt", "*")
    devDependency("ts-node", "^5.0.1")
  }
}