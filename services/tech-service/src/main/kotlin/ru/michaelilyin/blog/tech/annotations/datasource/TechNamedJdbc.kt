package ru.michaelilyin.blog.tech.annotations.datasource

import org.springframework.beans.factory.annotation.Qualifier

@Qualifier()
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class TechNamedJdbc {
}