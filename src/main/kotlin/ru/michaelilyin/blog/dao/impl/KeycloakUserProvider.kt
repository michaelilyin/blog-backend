package ru.michaelilyin.blog.dao.impl

import mu.KLogging
import org.apache.http.client.utils.URIBuilder
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import ru.michaelilyin.blog.dao.UserRepository
import ru.michaelilyin.blog.model.KeycloakUser

@Repository
class KeycloakUserProvider @Autowired() constructor(
        @Value("\${kk.api.realm}") private val realm: String,
        @Lazy() private val template: KeycloakRestTemplate
) : UserRepository {

    companion object : KLogging()

    override fun getUsers(search: String?): List<KeycloakUser> {
        val uri = URIBuilder("${realm}/users")
        if (search != null) {
            uri.addParameter("search", search)
        }

        val users = this.template.getForEntity(uri.build(), Array<KeycloakUser>::class.java)
        val body = users.body
        if (body != null) {
            return body.asList()
        } else {
            return arrayListOf()
        }
    }

}