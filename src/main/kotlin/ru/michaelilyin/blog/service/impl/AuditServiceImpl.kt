package ru.michaelilyin.blog.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.set
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.annotations.notification.EventType
import ru.michaelilyin.blog.annotations.notification.NotifyChanges
import ru.michaelilyin.blog.dao.AuditRepository
import ru.michaelilyin.blog.dao.AuditSettingsRepository
import ru.michaelilyin.blog.dto.AuditRecordDTO
import ru.michaelilyin.blog.dto.TraceElementDTO
import ru.michaelilyin.blog.model.AuditRecord
import ru.michaelilyin.blog.model.AuditSetting
import ru.michaelilyin.blog.service.AuditService
import ru.michaelilyin.blog.service.AuthenticationFacade
import java.time.LocalDateTime

@Service
class AuditServiceImpl @Autowired() constructor(
        private val mapper: ObjectMapper,
        private val authenticationFacade: AuthenticationFacade,
        private val auditRepository: AuditRepository,
        private val auditSettingsRepository: AuditSettingsRepository
) : AuditService {

    @NotifyChanges(tag = "audit", event = EventType.CREATION)
    override fun createAuditRecord(tag: String, severity: AuditLevel, message: String, throwable: Throwable?): AuditRecordDTO? {
        val name = authenticationFacade.getUserLogin() ?: "Anonymous"

        val setting = auditSettingsRepository.getSettingsFor(tag, name)
        val settingSeverity = setting.severity?.level ?: AuditLevel.INFO.level
        if (settingSeverity < severity.level) {
            return null
        }

        val thread = Thread.currentThread()
        val threadName = thread.name

        val collectTrace = throwable != null || setting.trace == true
        val trace = if (collectTrace) constructTraceString(throwable) else null

        val record = AuditRecord(
                id = 0,
                tag = tag,
                thread = threadName,
                time = LocalDateTime.now(),
                severity = severity,
                login = name,
                message = message,
                trace = trace
        )
        val result = auditRepository.createAuditRecord(record)
        return AuditRecordDTO(result, mapper)
    }

    private fun constructTraceString(throwable: Throwable?): String? {
        if (throwable == null) {
            return null
        }
        val list = mutableListOf<TraceElementDTO>()
        val trace = throwable.stackTrace
        list.addAll(trace.map {
            TraceElementDTO(
                    clazz = it.className,
                    file = it.fileName,
                    method = it.methodName,
                    line = it.lineNumber
            )
        })
        //TODO: write all exceptions
        return mapper.writeValueAsString(list)
    }

    @Secured("ROLE_audit_read")
    override fun getAuditRecords(offset: Long, limit: Long): List<AuditRecordDTO> {
        val records = auditRepository.getAuditRecords(offset, limit)
        return records.map {
            AuditRecordDTO(it, mapper)
        }
    }
}