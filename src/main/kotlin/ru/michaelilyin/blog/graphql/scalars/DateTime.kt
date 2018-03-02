package ru.michaelilyin.blog.graphql.scalars

import graphql.language.StringValue
import java.time.LocalDateTime
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class GraphQLLocalDateTime : GraphQLScalarType(
        "DateTime",
        "Date Time type",
        object : Coercing<LocalDateTime, String> {
            override fun serialize(input: Any): String {
                if (input is LocalDateTime) {
                    return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(input)
                }
                throw CoercingSerializeException("Invalid value '$input' for LocalDateTime")
            }

            override fun parseValue(input: Any): LocalDateTime {
                if (input is String) {
                    return LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                }
                throw CoercingParseValueException("Invalid value '$input' for LocalDateTime")
            }

            override fun parseLiteral(input: Any): LocalDateTime? {
                if (input is StringValue) {
                    return LocalDateTime.parse(input.value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                }
                throw CoercingParseValueException("Invalid value '$input' for LocalDateTime")
            }
        })