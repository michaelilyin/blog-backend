package ru.michaelilyin.blog.graphql.scalars

import graphql.language.StringValue
import java.time.LocalDateTime
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import mu.KLogging
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class GraphQLLocalDateTime : GraphQLScalarType(
        "DateTime",
        "Date Time type",
        object : Coercing<LocalDateTime, String> {
            override fun serialize(input: Any): String {
                if (input is LocalDateTime) {
                    return DateTimeFormatter.ISO_ZONED_DATE_TIME.format(
                            input.atZone(default).withZoneSameInstant(utc)
                    )
                }
                throw CoercingSerializeException("Invalid value '$input' for LocalDateTime")
            }

            override fun parseValue(input: Any): LocalDateTime {
                if (input is String) {
                    return ZonedDateTime.parse(input, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                            .withZoneSameInstant(default)
                            .toLocalDateTime()
                }
                throw CoercingParseValueException("Invalid value '$input' for LocalDateTime")
            }

            override fun parseLiteral(input: Any): LocalDateTime? {
                if (input is StringValue) {
                    return ZonedDateTime.parse(input.value, DateTimeFormatter.ISO_ZONED_DATE_TIME)
                            .withZoneSameInstant(default)
                            .toLocalDateTime()
                }
                throw CoercingParseValueException("Invalid value '$input' for LocalDateTime")
            }
        }) {

    companion object: KLogging() {
        val utc = ZoneId.of("UTC")!!
        val default = ZoneId.systemDefault()!!
    }

}