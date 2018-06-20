package ru.michaelilyin.blog.dao

import ru.michaelilyin.blog.model.KeycloakClient

interface AuthClientRepository {
    fun getClients(): List<KeycloakClient>
}