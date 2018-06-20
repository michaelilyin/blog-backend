package ru.michaelilyin.blog.dto

data class UserDTO(
        var id: String,
        var login: String,
        var email: String,
        var firstName: String,
        var lastName: String
)