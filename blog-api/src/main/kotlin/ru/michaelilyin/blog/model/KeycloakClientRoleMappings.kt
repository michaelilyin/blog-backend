package ru.michaelilyin.blog.model

import javax.management.monitor.StringMonitor

data class KeycloakClientRoleMappings(
        var id: String,
        var client: String,
        var mappings: List<KeycloakRole>
)