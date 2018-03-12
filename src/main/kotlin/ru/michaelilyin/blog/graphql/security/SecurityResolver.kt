package ru.michaelilyin.blog.graphql.security

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import graphql.servlet.GraphQLQueryProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.GroupDTO
import ru.michaelilyin.blog.dto.PermissionDTO
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.service.PermissionService
import ru.michaelilyin.blog.service.RoleService

@Component
class SecurityResolver @Autowired() constructor(
        private val roleService: RoleService,
        private val permissionService: PermissionService
) : GraphQLQueryResolver {

    fun roles(search: String?): List<RoleDTO> {
        return roleService.getRoles(search)
    }

    fun permissions(search: String?): List<PermissionDTO> {
        return permissionService.getPermissions(search)
    }

    fun groups(search: String?): List<GroupDTO> {
        return arrayListOf()
    }

}