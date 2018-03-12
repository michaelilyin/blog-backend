package ru.michaelilyin.blog.config

import com.oembedler.moon.graphql.boot.GraphQLServletProperties
import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import graphql.execution.ExecutionStrategy
import graphql.execution.instrumentation.Instrumentation
import graphql.execution.preparsed.PreparsedDocumentProvider
import graphql.servlet.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GraphQLConfig {

    @Autowired(required = false)
    private val listeners: List<GraphQLServletListener>? = null

    @Autowired(required = false)
    private val instrumentation: Instrumentation? = null

    @Autowired(required = false)
    private val contextBuilder: GraphQLContextBuilder? = null

    @Autowired(required = false)
    private val graphQLRootObjectBuilder: GraphQLRootObjectBuilder? = null

    @Autowired(required = false)
    private val objectMapperConfigurer: ObjectMapperConfigurer? = null

    @Autowired(required = false)
    private val preparsedDocumentProvider: PreparsedDocumentProvider? = null

    @Bean
    fun graphQLServlet(schemaProvider: GraphQLSchemaProvider,
                       executionStrategyProvider: ExecutionStrategyProvider): GraphQLServlet {
        return object : SimpleGraphQLServlet(
                schemaProvider,
                executionStrategyProvider,
                objectMapperConfigurer,
                listeners,
                instrumentation,
                ErrorHandler(),
                contextBuilder,
                graphQLRootObjectBuilder,
                preparsedDocumentProvider
        ) {

        }
    }

    class ErrorHandler : DefaultGraphQLErrorHandler() {
        override fun processErrors(errors: MutableList<GraphQLError>): MutableList<GraphQLError> {
            val processed: MutableList<GraphQLError> = errors.asSequence()
                    .filter { it is Throwable || it is ExceptionWhileDataFetching }
                    .map {
                        when (it) {
                            is Throwable -> GenericGraphQLError(it.message)
                            is ExceptionWhileDataFetching -> GenericGraphQLError(it.exception.message)
                            else -> null
                        }
                    }
                    .filter { it != null }
                    .map { it as GraphQLError }
                    .toMutableList()
            if (processed.size == 0) {
                return super.processErrors(errors)
            } else {
                return processed
            }
        }
    }
}