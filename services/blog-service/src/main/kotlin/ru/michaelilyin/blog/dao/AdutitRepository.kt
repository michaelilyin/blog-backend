package ru.michaelilyin.blog.dao

import ru.michaelilyin.blog.model.AuditRecord

interface AuditRepository {
    fun createAuditRecord(record: AuditRecord): AuditRecord
    fun getAuditRecords(offset: Long, limit: Long): List<AuditRecord>
}