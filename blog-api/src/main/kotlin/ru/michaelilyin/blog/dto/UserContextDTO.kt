package ru.michaelilyin.blog.dto

data class UserContextDTO(
        var username: String,
        var roles: List<String>,
        var authorities: List<String>
) {
}