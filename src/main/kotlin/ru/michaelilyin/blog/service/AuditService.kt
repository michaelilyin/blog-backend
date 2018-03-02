package ru.michaelilyin.blog.service

import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.dto.AuditRecordDTO

interface AuditService {
    fun createAuditRecord(tag: String, severity: AuditLevel, message: String, throwable: Throwable? = null): AuditRecordDTO
    fun getAuditRecords(offset: Long, limit: Long): List<AuditRecordDTO>
}