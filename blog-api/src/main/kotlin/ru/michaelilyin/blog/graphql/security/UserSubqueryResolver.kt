package ru.michaelilyin.blog.graphql.security

import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.GroupDTO
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.dto.UserDTO
import ru.michaelilyin.blog.service.RoleService

@Component
class UserSubqueryResolver @Autowired() constructor(
        private val roleService: RoleService
) : GraphQLResolver<UserDTO> {
    fun roles(user: UserDTO): List<RoleDTO> {
        return roleService.getRolesOfUser(user.id)
    }

    fun groups(user: UserDTO): List<GroupDTO> {
        return emptyList()
    }
}