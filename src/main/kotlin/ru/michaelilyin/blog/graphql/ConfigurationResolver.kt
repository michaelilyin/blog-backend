package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.dto.ConfigurationDTO

@Component
class ConfigurationResolver : GraphQLQueryResolver {
    fun configuration(): ConfigurationDTO {
        return ConfigurationDTO(
                "Unnamed"
        )
    }
}