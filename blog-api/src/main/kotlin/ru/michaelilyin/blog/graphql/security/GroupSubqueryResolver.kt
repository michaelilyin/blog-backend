package ru.michaelilyin.blog.graphql.security

import com.coxautodev.graphql.tools.GraphQLResolver
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.GroupDTO
import ru.michaelilyin.blog.dto.RoleDTO

@Component
class GroupSubqueryResolver: GraphQLResolver<GroupDTO> {
    fun roles(group: GroupDTO): List<RoleDTO> {
        return emptyList()
    }
}