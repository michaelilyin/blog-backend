package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.model.AuditRecord
import ru.michaelilyin.blog.service.AuditService

@Component
class AuditResolver @Autowired() constructor(
        private val auditService: AuditService
) : GraphQLQueryResolver {
    fun audit(offset: Long, limit: Long): List<AuditRecord> {
        return auditService.getAuditRecords(offset, limit)
    }
}