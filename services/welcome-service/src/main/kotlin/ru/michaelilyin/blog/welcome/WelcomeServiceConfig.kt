package ru.michaelilyin.blog.welcome

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackageClasses = [WelcomeServiceConfig::class])
@PropertySource("classpath:/ru/michaelilyin/blog/welcome/properties.properties")
class WelcomeServiceConfig {

}