package ru.michaelilyin.blog.dao.impl

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import ru.michaelilyin.blog.annotations.cache.repository.CacheableRepository
import ru.michaelilyin.blog.dao.AuthClientRepository
import ru.michaelilyin.blog.model.KeycloakClient

@CacheableRepository
class KeycloakAuthClientProvider @Autowired() constructor(
        @Value("\${kk.api.realm}") private val realm: String
) : AuthClientRepository {

    companion object : KLogging()

    override fun getClients(): List<KeycloakClient> {
        throw NotImplementedError()
    }

}