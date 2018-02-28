package ru.michaelilyin.blog.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.dto.AuditDTO
import ru.michaelilyin.blog.service.AuditService

@Service
class AuditServiceImpl @Autowired() constructor() : AuditService {

    @Audit(AuditLevel.DEBUG)
    @PreAuthorize("""hasRole("audit-read")""")
    override fun getAudit(): List<AuditDTO> {
        return listOf(AuditDTO("1", "test"))
    }
}