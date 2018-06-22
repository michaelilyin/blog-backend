package ru.michaelilyin.blog.model

import ru.michaelilyin.blog.annotations.audit.AuditLevel

data class AuditSetting(
        var id: Long,
        var tag: String?,
        var login: String?,
        var severity: AuditLevel?,
        var trace: Boolean?
) {
}