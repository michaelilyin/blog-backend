package ru.michaelilyin.blog.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.annotations.notification.EventType
import ru.michaelilyin.blog.annotations.notification.NotifyChanges
import ru.michaelilyin.blog.dao.AuditRepository
import ru.michaelilyin.blog.dao.AuditSettingsRepository
import ru.michaelilyin.blog.model.AuditRecord
import ru.michaelilyin.blog.model.AuditSetting
import ru.michaelilyin.blog.service.AuditService
import java.time.LocalDateTime

@Service
class AuditServiceImpl @Autowired() constructor(
        private val mapper: ObjectMapper,
//        private val authenticationFacade: AuthenticationFacade,
        private val auditRepository: AuditRepository,
        private val auditSettingsRepository: AuditSettingsRepository
) : AuditService {

    companion object : KLogging()

    @NotifyChanges(tag = "audit", event = EventType.CREATION)
    override fun createAuditRecord(tag: String, severity: AuditLevel, message: String, throwable: Throwable?): AuditRecord? {
        val name = /*authenticationFacade.getUserLogin() ?:*/ "Anonymous"

        val setting = auditSettingsRepository.getSettingsFor(tag, name)
        logger.debug { "Process audit creation with args ($tag, $severity) and settings $setting" }

        val settingSeverity = setting.severity?.level ?: AuditLevel.INFO.level
        if (settingSeverity < severity.level) {
            return null
        }

        val result = createAuditRecordInternal(name, tag, severity, message, throwable, setting)
        return result
    }

//    @Secured("ROLE_audit-read")
    override fun getAuditRecords(offset: Long, limit: Long): List<AuditRecord> {
        val records = auditRepository.getAuditRecords(offset, limit)
        return records
    }

    private fun createAuditRecordInternal(username: String, tag: String, severity: AuditLevel, message: String,
                                          throwable: Throwable?, setting: AuditSetting): AuditRecord {
        val thread = Thread.currentThread()
        val threadName = thread.name

        val collectTrace = throwable != null || setting.trace == true
        val trace = /*if (collectTrace) constructTraceString(throwable) else*/ null

        val record = AuditRecord(
                id = 0,
                tag = tag,
                thread = threadName,
                time = LocalDateTime.now(),
                severity = severity,
                login = username,
                message = message,
                trace = trace
        )
        val result = auditRepository.createAuditRecord(record)
        return result
    }

//    private fun constructTraceString(throwable: Throwable?): String? {
//        val containers = mutableListOf<TraceContainerDTO>()
//        if (throwable == null) {
//            val thread = Thread.currentThread()
//            val trace = thread.stackTrace
//            val elements = convertTrace(trace)
//            val container = TraceContainerDTO(
//                    state = TraceState.NORMAL,
//                    message = "No message",
//                    reporter = "Audit",
//                    trace = elements
//            )
//            containers.add(container)
//        } else {
//            constructExceptionTraceContainer(throwable, containers)
//        }
//        return mapper.writeValueAsString(containers)
//    }
//
//    private fun constructExceptionTraceContainer(throwable: Throwable, containers: MutableList<TraceContainerDTO>) {
//        var ex: Throwable? = throwable
//        while (ex != null) {
//            val trace = ex.stackTrace
//            val elements = convertTrace(trace)
//            val container = TraceContainerDTO(
//                    state = TraceState.ERROR,
//                    message = ex.message ?: "No message",
//                    reporter = ex.javaClass.canonicalName,
//                    trace = elements
//            )
//            containers.add(container)
//            ex = ex.cause
//        }
//    }
//
//    private fun convertTrace(trace: Array<out StackTraceElement>): List<TraceElementDTO> {
//        return trace
//                .map {
//                    TraceElementDTO(
//                            clazz = it.className,
//                            file = it.fileName,
//                            method = it.methodName,
//                            line = it.lineNumber
//                    )
//                }
//    }
}