package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import graphql.servlet.GraphQLQueryProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.service.RoleService

@Component
class RoleResolver @Autowired() constructor(
        private val roleService: RoleService
) : GraphQLQueryResolver {

    fun roles(search: String?): List<RoleDTO> {
        return roleService.getRoles(search)
    }

}