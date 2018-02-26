package ru.michaelilyin.blog.annotations.notification

enum class EventType {
    CREATION, CHANGE, DELETION
}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Repeatable
annotation class NotifyChanges(
        val tag: String,
        val event: EventType
)