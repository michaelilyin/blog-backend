package ru.michaelilyin.blog.annotations.audit

enum class AuditLevel {
    INFO, DEBUG, ERROR
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Audit(
        val level: AuditLevel = AuditLevel.DEBUG
)