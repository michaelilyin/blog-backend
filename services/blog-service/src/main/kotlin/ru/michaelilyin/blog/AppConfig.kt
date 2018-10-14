package ru.michaelilyin.blog

import org.springframework.boot.autoconfigure.AutoConfigurationPackage
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackageClasses = [AppConfig::class])
@PropertySource("classpath:/properties.properties")
class AppConfig {
}