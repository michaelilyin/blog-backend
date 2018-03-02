package ru.michaelilyin.blog.dto

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.model.AuditRecord
import java.time.LocalDateTime

data class TraceElementDTO(
        val clazz: String,
        val method: String,
        val file: String?,
        val line: Int
) {
}

data class AuditRecordDTO(
        val id: String,
        val tag: String,
        val thread: String,
        val time: LocalDateTime,
        val severity: AuditLevel,
        val login: String,
        val message: String,
        val trace: List<TraceElementDTO>?
) {

    companion object {
        private fun parseTrace(trace: String?, mapper: ObjectMapper): List<TraceElementDTO>? {
            if (trace == null) {
                return null
            }
            return mapper.readValue(trace, object : TypeReference<List<TraceElementDTO>>() {})
        }
    }

    constructor(model: AuditRecord, mapper: ObjectMapper) : this(
            id = model.id.toString(),
            tag = model.tag,
            thread = model.thread,
            time = model.time,
            severity = model.severity,
            login = model.login,
            message = model.message,
            trace = parseTrace(model.trace, mapper)
    )

}