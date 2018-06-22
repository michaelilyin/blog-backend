package ru.michaelilyin.blog.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import graphql.GraphqlErrorHelper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ExceptionHandler

@Component
class QueryResolver @Autowired() constructor(
        @Value("\${build.version}") private val version: String,
        @Value("\${build.time}") private val time: String
) : GraphQLQueryResolver {

    fun version() = version
    fun buildTime() = time
}