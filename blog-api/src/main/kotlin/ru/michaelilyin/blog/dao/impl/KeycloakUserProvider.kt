package ru.michaelilyin.blog.dao.impl

import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import ru.michaelilyin.blog.dao.UserRepository
import ru.michaelilyin.blog.model.KeycloakUser

@Repository
class KeycloakUserProvider @Autowired() constructor(
) : UserRepository {

    companion object : KLogging()

    override fun getUsers(search: String?): List<KeycloakUser> {
        throw NotImplementedError()
    }

}