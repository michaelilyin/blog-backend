package ru.michaelilyin.blog.dao.impl

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
        @Value("\${kk.auth.realm}") private val authRealm: String
) : UserRepository {

    companion object : KLogging()

    override fun getUsers(search: String?): List<KeycloakUser> {
        val rest = RestTemplate()

        try {
            val token = ""
            val headers = HttpHeaders()
            headers.set("Authorization", "bearer ${token}")

            val res = rest.exchange("${realm}/users", HttpMethod.GET, HttpEntity<Any>(headers), Array<KeycloakUser>::class.java)
            if (res.statusCode == HttpStatus.OK) {
                return res.body!!.asList()
            }
        } catch (e: Exception) {
            logger.error(e) { e }
        }

        throw IllegalStateException()
    }

}