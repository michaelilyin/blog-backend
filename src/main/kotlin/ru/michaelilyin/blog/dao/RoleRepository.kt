package ru.michaelilyin.blog.dao

import ru.michaelilyin.blog.model.KeycloakRole
import ru.michaelilyin.blog.model.KeycloakUser

interface RoleRepository {
    fun getRoles(client: String? = null): List<KeycloakRole>
}