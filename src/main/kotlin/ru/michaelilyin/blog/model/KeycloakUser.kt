package ru.michaelilyin.blog.model

data class KeycloakUser(var id: String,
                        var username: String,
                        var email: String,
                        var firstName: String,
                        var lastName: String) {
}