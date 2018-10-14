package ru.michaelilyin.blog.tech

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackageClasses = [TechServiceConfig::class])
@PropertySource("classpath:/ru/michaelilyin/blog/tech/properties.properties")
class TechServiceConfig {

}