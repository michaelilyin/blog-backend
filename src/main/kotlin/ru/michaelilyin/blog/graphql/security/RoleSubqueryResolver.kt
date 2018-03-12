package ru.michaelilyin.blog.graphql.security

import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.PermissionDTO
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.service.PermissionService

@Component
class RoleSubqueryResolver @Autowired() constructor(
        private val permissionService: PermissionService
): GraphQLResolver<RoleDTO> {
    fun permissions(role: RoleDTO): List<PermissionDTO> {
        return permissionService.getPermissionsOfRole(role.id)
    }
}