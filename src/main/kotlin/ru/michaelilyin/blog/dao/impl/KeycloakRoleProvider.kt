package ru.michaelilyin.blog.dao.impl

import mu.KLogging
import org.apache.http.client.utils.URIBuilder
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Repository
import ru.michaelilyin.blog.dao.RoleRepository
import ru.michaelilyin.blog.model.KeycloakRole
import ru.michaelilyin.blog.model.KeycloakRoleMapping

@Repository
class KeycloakRoleProvider @Autowired() constructor(
        @Value("\${kk.api.realm}") private val realm: String,
        @Lazy() private val template: KeycloakRestTemplate
) : RoleRepository {

    companion object : KLogging()

    override fun getRoles(client: String?): List<KeycloakRole> {
        val base = if (client == null) "${realm}/roles" else "${realm}/clients/${client}/roles"
        val uri = URIBuilder(base)

        val roles = this.template.getForEntity(uri.build(), Array<KeycloakRole>::class.java)
        val body = roles.body
        return body?.asList() ?: arrayListOf()
    }

    override fun getNestedRoles(id: String): List<KeycloakRole> {
        val base = "${realm}/roles-by-id/${id}/composites"
        val uri = URIBuilder(base)

        val roles = this.template.getForEntity(uri.build(), Array<KeycloakRole>::class.java)
        val body = roles.body
        return body?.asList() ?: arrayListOf()
    }

    override fun getRolesOfUser(id: String): KeycloakRoleMapping {
        val base = "${realm}/users/${id}/role-mappings"
        val uri = URIBuilder(base)

//        val users = this.template.getForEntity(uri.build(), String::class.java)
//        logger.warn { users }
//        return emptyList()
        val mapping = this.template.getForEntity(uri.build(), KeycloakRoleMapping::class.java)
        val body = mapping.body
        return body ?: KeycloakRoleMapping(emptyList(), emptyMap())
    }
}