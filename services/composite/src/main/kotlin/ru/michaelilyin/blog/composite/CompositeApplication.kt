package ru.michaelilyin.blog.composite

import mu.KLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import ru.michaelilyin.blog.tech.TechServiceConfig
import ru.michaelilyin.blog.welcome.WelcomeServiceConfig

@SpringBootApplication
@PropertySource("classpath:/ru/michaelilyin/blog/composite/properties.properties")
@Import(
  TechServiceConfig::class,
  WelcomeServiceConfig::class
)
class CompositeApplication

val logger = KLogging().logger

fun main(args: Array<String>) {
  runApplication<CompositeApplication>(*args)
}