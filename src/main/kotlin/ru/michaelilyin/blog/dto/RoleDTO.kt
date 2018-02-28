package ru.michaelilyin.blog.dto

data class RoleDTO(
        var id: String,
        var name: String,
        var client: String,
        var description: String?
) {
}