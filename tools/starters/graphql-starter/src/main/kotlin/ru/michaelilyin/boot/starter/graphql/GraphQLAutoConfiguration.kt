package ru.michaelilyin.boot.starter.graphql

import com.oembedler.moon.graphql.boot.GraphQLJavaToolsAutoConfiguration
import com.oembedler.moon.graphql.boot.GraphQLWebAutoConfiguration
import graphql.ExceptionWhileDataFetching
import graphql.GraphQLError
import graphql.execution.instrumentation.Instrumentation
import graphql.execution.preparsed.PreparsedDocumentProvider
import graphql.servlet.*
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(basePackageClasses = [GraphQLAutoConfiguration::class])
@AutoConfigureAfter(GraphQLJavaToolsAutoConfiguration::class)
@AutoConfigureBefore(GraphQLWebAutoConfiguration::class)
class GraphQLAutoConfiguration {

  @Configuration
  class GraphQLAutoConfiguration {
    companion object : KLogging()

    @Autowired(required = false) private val listeners: List<GraphQLServletListener>? = null;
    @Autowired(required = false) private val instrumentation: Instrumentation? = null;
    @Autowired(required = false) private val contextBuilder: GraphQLContextBuilder? = null;
    @Autowired(required = false) private val graphQLRootObjectBuilder: GraphQLRootObjectBuilder? = null;
    @Autowired(required = false) private val objectMapperConfigurer: ObjectMapperConfigurer? = null;
    @Autowired(required = false) private val preparsedDocumentProvider: PreparsedDocumentProvider? = null;

    @Bean
    fun graphQLServlet(schemaProvider: GraphQLSchemaProvider,
                       executionStrategyProvider: ExecutionStrategyProvider): GraphQLServlet {
      logger.info { "Create GraphQL servlet" }
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
      init {
        logger.info { "Create GraphQL error handler" }
      }

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
}