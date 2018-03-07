package ru.michaelilyin.blog.dao;

import ru.michaelilyin.blog.model.AuditSetting;

interface AuditSettingsRepository {
    fun getSettingsFor(tag: String, login: String): AuditSetting
}
