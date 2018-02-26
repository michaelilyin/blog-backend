package ru.michaelilyin.blog.annotations.audit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Audit(
        val errorOnly: Boolean = true
)