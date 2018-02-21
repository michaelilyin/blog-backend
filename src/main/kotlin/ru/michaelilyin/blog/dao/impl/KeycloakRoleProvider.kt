package ru.michaelilyin.blog.dao.impl

import mu.KLogging
import org.apache.http.client.utils.URIBuilder
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import ru.michaelilyin.blog.dao.RoleRepository
import ru.michaelilyin.blog.model.KeycloakRole

@Repository
class KeycloakRoleProvider @Autowired() constructor(
        @Value("\${kk.api.realm}") private val realm: String,
        private val template: KeycloakRestTemplate
) : RoleRepository {

    companion object : KLogging()

    override fun getRoles(client: String?): List<KeycloakRole> {
        val base = if (client == null) "${realm}/roles" else "${realm}/clients/${client}/roles"
        val uri = URIBuilder(base)

        val users = this.template.getForEntity(uri.build(), Array<KeycloakRole>::class.java)
        val body = users.body
        if (body != null) {
            return body.asList()
        } else {
            return arrayListOf()
        }
    }

}