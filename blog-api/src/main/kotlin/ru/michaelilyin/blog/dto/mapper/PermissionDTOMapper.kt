package ru.michaelilyin.blog.dto.mapper

import ru.michaelilyin.blog.dto.PermissionDTO
import ru.michaelilyin.blog.model.KeycloakRole

object PermissionDTOMapper {
    fun toDTO(role: KeycloakRole): PermissionDTO {
        return PermissionDTO(
                id = role.id,
                name = role.name,
                description = role.description
        )
    }
}