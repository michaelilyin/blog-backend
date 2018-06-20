package ru.michaelilyin.blog.dto

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.model.AuditRecord
import java.time.LocalDateTime

enum class TraceState {
    NORMAL, ERROR
}

data class TraceContainerDTO(
        val state: TraceState,
        val reporter: String,
        val message: String,
        val trace: List<TraceElementDTO>
)

data class TraceElementDTO(
        val clazz: String,
        val method: String,
        val file: String?,
        val line: Int
)

data class AuditRecordDTO(
        val id: String,
        val tag: String,
        val thread: String,
        val time: LocalDateTime,
        val severity: AuditLevel,
        val login: String,
        val message: String,
        val trace: List<TraceContainerDTO>?
)