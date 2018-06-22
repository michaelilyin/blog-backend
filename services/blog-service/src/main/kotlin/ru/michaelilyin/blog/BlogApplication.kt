package ru.michaelilyin.blog

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import ru.michaelilyin.blog.config.SecurityConfig

@Import(SecurityConfig::class)
@SpringBootApplication
@PropertySource("classpath:/properties.properties")
class BlogApplication {

}

fun main(args: Array<String>) {
    runApplication<BlogApplication>(*args)
}
