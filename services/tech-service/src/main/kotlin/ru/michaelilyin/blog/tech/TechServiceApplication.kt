package ru.michaelilyin.blog.tech

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.PropertySource

@EnableDiscoveryClient
@SpringBootApplication
@PropertySource("classpath:/properties.properties")
class TechServiceApplication

fun main(args: Array<String>) {
  runApplication<TechServiceApplication>(*args)
}