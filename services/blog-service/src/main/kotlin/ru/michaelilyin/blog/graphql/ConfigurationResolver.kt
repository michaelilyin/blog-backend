package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component
import ru.michaelilyin.blog.model.Configuration

@Component
class ConfigurationResolver : GraphQLQueryResolver {
    fun configuration(): Configuration {
        return Configuration(
                "Unnamed"
        )
    }
}