package ru.michaelilyin.blog.dto.mapper

import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.model.KeycloakRole

object RoleDTOMapper {
    fun toDTO(role: KeycloakRole): RoleDTO {
        return RoleDTO(
                role.id,
                role.name,
                role.description
        )
    }
}