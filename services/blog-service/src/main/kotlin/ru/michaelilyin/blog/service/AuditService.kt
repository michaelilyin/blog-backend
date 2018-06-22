package ru.michaelilyin.blog.service

import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.model.AuditRecord

interface AuditService {
    fun createAuditRecord(tag: String, severity: AuditLevel, message: String, throwable: Throwable? = null): AuditRecord?
    fun getAuditRecords(offset: Long, limit: Long): List<AuditRecord>
}