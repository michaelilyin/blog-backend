package ru.michaelilyin.blog.model

data class KeycloakRoleMapping(
        var realmMappings: List<KeycloakRole>,
        var clientMappings: Map<String, KeycloakClientRoleMappings>
)