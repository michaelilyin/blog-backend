package ru.michaelilyin.blog.dao

import ru.michaelilyin.blog.model.KeycloakRole
import ru.michaelilyin.blog.model.KeycloakRoleMapping
import ru.michaelilyin.blog.model.KeycloakUser

interface RoleRepository {
    fun getRoles(client: String? = null): List<KeycloakRole>
    fun getNestedRoles(id: String): List<KeycloakRole>
    fun getRolesOfUser(id: String): KeycloakRoleMapping
}