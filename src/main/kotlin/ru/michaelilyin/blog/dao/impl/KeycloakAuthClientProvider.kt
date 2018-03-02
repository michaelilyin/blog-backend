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
import ru.michaelilyin.blog.dao.AuthClientRepository
import ru.michaelilyin.blog.dao.UserRepository
import ru.michaelilyin.blog.model.KeycloakClient
import ru.michaelilyin.blog.model.KeycloakUser

@Repository
class KeycloakAuthClientProvider @Autowired() constructor(
        @Value("\${kk.api.realm}") private val realm: String,
        @Lazy() private val template: KeycloakRestTemplate
) : AuthClientRepository {

    companion object : KLogging()

    override fun getClients(): List<KeycloakClient> {
        val uri = URIBuilder("${realm}/clients")

        val users = this.template.getForEntity(uri.build(), Array<KeycloakClient>::class.java)
        val body = users.body
        if (body != null) {
            return body.asList()
        } else {
            return arrayListOf()
        }
    }

}