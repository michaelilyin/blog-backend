package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.UserContextDTO
import ru.michaelilyin.blog.service.UserContextService

@Component
class UserContextResolver @Autowired() constructor(
        private val userContextService: UserContextService
) : GraphQLQueryResolver {

    companion object : KLogging()

    fun userContext(): UserContextDTO? {
        return userContextService.getUserContext();
    }
}