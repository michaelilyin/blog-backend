package ru.michaelilyin.blog.dao.impl

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import ru.michaelilyin.blog.dao.RoleRepository
import ru.michaelilyin.blog.model.KeycloakRole
import ru.michaelilyin.blog.model.KeycloakRoleMapping

@Repository
class KeycloakRoleProvider @Autowired() constructor(
) : RoleRepository {

    companion object : KLogging()

    override fun getRoles(client: String?): List<KeycloakRole> {
        throw NotImplementedError()
    }

    override fun getNestedRoles(id: String): List<KeycloakRole> {
        throw NotImplementedError()
    }

    override fun getRolesOfUser(id: String): KeycloakRoleMapping {
        throw NotImplementedError()
    }
}