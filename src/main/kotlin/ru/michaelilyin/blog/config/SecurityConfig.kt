package ru.michaelilyin.blog.config

import mu.KLogging
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig {

    companion object : KLogging()

    init {
        logger.info { "Security configuration created" }
    }
}
