package ru.michaelilyin.blog.tech

import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.PropertySource
import ru.michaelilyin.boot.starter.graphql.GraphQLAutoConfiguration

//@EnableDiscoveryClient
@SpringBootApplication
class TechServiceApplication {

}

fun main(args: Array<String>) {
  runApplication<TechServiceApplication>(*args)
}