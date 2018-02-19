package ru.michaelilyin.blog

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("properties.properties")
class BlogApplication {

}

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}
