package ru.michaelilyin.blog.annotations.audit

enum class AuditLevel(val level: Int) {
    ERROR(10),
    INFO(20),
    DEBUG(30)
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Audit(
        val level: AuditLevel = AuditLevel.DEBUG
)