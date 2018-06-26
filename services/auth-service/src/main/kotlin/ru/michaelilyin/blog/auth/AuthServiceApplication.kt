package ru.michaelilyin.blog.auth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.PropertySource


@EnableDiscoveryClient
@SpringBootApplication
@PropertySource("classpath:/properties.properties")
class AuthServiceApplication {

}

fun main(args: Array<String>) {
    runApplication<AuthServiceApplication>(*args)
}