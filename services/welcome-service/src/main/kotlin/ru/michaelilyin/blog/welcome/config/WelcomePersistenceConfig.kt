package ru.michaelilyin.blog.welcome.config

import liquibase.integration.spring.SpringLiquibase
import mu.KLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import ru.michaelilyin.blog.welcome.annotations.datasource.*
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class WelcomePersistenceConfig {
  companion object : KLogging()

  @Bean
  @WelcomeDataSourceProperties
  @ConfigurationProperties(prefix = "spring.datasource-welcome")
  fun welcomeDataSourceProperties(): DataSourceProperties {
    return DataSourceProperties()
  }

  @Bean
  @WelcomeDataSource
  fun welcomeDataSource(@WelcomeDataSourceProperties props: DataSourceProperties): DataSource {
    return props.initializeDataSourceBuilder().build()
  }

  @Bean
  @WelcomeJdbc
  fun welcomeJdbc(@WelcomeDataSource ds: DataSource): JdbcTemplate {
    return JdbcTemplate(ds)
  }

  @Bean
  @WelcomeNamedJdbc
  fun welcomeNamedJdbc(@WelcomeDataSource ds: DataSource): NamedParameterJdbcTemplate {
    return NamedParameterJdbcTemplate(ds)
  }

  @Bean
  @Qualifier("welcomeTX")
  fun welcomeTransactionManager(@WelcomeDataSource ds: DataSource): PlatformTransactionManager {
    return DataSourceTransactionManager(ds)
  }

  @Bean
  @DependsOn("liquibase")
  fun welcomeLiquibase(@WelcomeDataSource ds: DataSource): SpringLiquibase {
    val liquibase = SpringLiquibase()
    liquibase.dataSource = ds
    liquibase.changeLog = "classpath:/db/changelog/welcome/db.changelog-welcome.yaml"
    return liquibase
  }
}