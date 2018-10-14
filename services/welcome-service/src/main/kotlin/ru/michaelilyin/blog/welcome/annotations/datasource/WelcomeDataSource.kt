package ru.michaelilyin.blog.welcome.annotations.datasource

import org.springframework.beans.factory.annotation.Qualifier

@Qualifier()
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class WelcomeDataSource {
}