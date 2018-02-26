package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Role
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.UserDTO
import ru.michaelilyin.blog.service.UserService
import javax.annotation.security.RolesAllowed

@Component
class UserResolver @Autowired() constructor(
        private val userService: UserService
) : GraphQLQueryResolver {

//    @PreAuthorize("hasAuthority('read-users')")
    fun users(search: String?): List<UserDTO> {
        return userService.getUsers(search)
    }

    fun user(id: String): UserDTO {
        return users(null).first({it.id == id})
    }
}