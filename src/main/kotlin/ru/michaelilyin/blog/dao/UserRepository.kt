package ru.michaelilyin.blog.dao

import ru.michaelilyin.blog.model.KeycloakUser

interface UserRepository {
    fun getUsers(search: String?): List<KeycloakUser>
}