package ru.michaelilyin.blog.dto.mapper

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import ru.michaelilyin.blog.dto.AuditRecordDTO
import ru.michaelilyin.blog.dto.TraceContainerDTO
import ru.michaelilyin.blog.dto.TraceElementDTO
import ru.michaelilyin.blog.model.AuditRecord

object AuditRecordDTOMapper {

    fun toDTO(model: AuditRecord, mapper: ObjectMapper): AuditRecordDTO {
        return AuditRecordDTO(
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

    private fun parseTrace(trace: String?, mapper: ObjectMapper): List<TraceContainerDTO>? {
        if (trace == null) {
            return null
        }
        return mapper.readValue(trace, object : TypeReference<List<TraceContainerDTO>>() {})
    }

}
