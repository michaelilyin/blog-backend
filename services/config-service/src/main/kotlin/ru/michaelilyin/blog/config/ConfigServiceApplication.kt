package ru.michaelilyin.blog.config

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.config.server.EnableConfigServer

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
class ConfigServiceApplication

fun main(args: Array<String>) {
  runApplication<ConfigServiceApplication>(*args)
}