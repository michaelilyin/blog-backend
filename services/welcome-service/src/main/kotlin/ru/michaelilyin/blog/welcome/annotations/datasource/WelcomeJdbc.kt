package ru.michaelilyin.blog.welcome.annotations.datasource

import org.springframework.beans.factory.annotation.Qualifier

@Qualifier()
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class WelcomeJdbc {
}