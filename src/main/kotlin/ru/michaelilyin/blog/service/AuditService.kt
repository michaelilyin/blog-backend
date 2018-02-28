package ru.michaelilyin.blog.service

import ru.michaelilyin.blog.dto.AuditDTO

interface AuditService {
    fun getAudit(): List<AuditDTO>
}