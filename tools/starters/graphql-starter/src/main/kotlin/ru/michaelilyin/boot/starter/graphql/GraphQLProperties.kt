package ru.michaelilyin.boot.starter.graphql

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "michaelilyin")
class GraphQLProperties {
  var version: String = "undefined"
  var buildTime: String = "undefined"
}