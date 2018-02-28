package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.AuditDTO
import ru.michaelilyin.blog.dto.ConfigurationDTO
import ru.michaelilyin.blog.service.AuditService

@Component
class AuditResolver @Autowired() constructor(
        private val auditService: AuditService
) : GraphQLQueryResolver {
    fun audit(): List<AuditDTO> {
        return auditService.getAudit()
    }
}