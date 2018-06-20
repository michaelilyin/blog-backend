package ru.michaelilyin.blog.model

data class KeycloakRole(
        var id: String,
        var name: String,
        var description: String?,
        var scopeParamRequired: Boolean?,
        var composite: Boolean?,
        var clientRole: Boolean?,
        var containerId: String?
) {
}