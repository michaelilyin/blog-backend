package ru.michaelilyin.blog.tech.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource
import liquibase.integration.spring.SpringLiquibase
import mu.KLogging
import org.springframework.context.annotation.DependsOn
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Transactional
import ru.michaelilyin.blog.tech.annotations.datasource.*


@Configuration
@EnableTransactionManagement
class TechPersistenceConfig {
  companion object : KLogging()

  @Bean
  @TechDataSourceProperties
  @ConfigurationProperties(prefix = "spring.datasource-tech")
  fun techDataSourceProperties(): DataSourceProperties {
    return DataSourceProperties()
  }

  @Bean
  @TechDataSource
  fun techDataSource(@TechDataSourceProperties props: DataSourceProperties): DataSource {
    return props.initializeDataSourceBuilder().build()
  }

  @Bean
  @TechJdbc
  fun techJdbc(@TechDataSource ds: DataSource): JdbcTemplate {
    return JdbcTemplate(ds)
  }

  @Bean
  @TechNamedJdbc
  fun techNamedJdbc(@TechDataSource ds: DataSource): NamedParameterJdbcTemplate {
    return NamedParameterJdbcTemplate(ds)
  }

  @Bean
  @TechTransactionManager
  fun techTransactionManager(@TechDataSource ds: DataSource): PlatformTransactionManager {
    return DataSourceTransactionManager(ds)
  }

  @Bean
  @DependsOn("liquibase-master")
  fun liquibase(@TechDataSource ds: DataSource): SpringLiquibase {
    val liquibase = SpringLiquibase()
    liquibase.dataSource = ds
    liquibase.changeLog = "classpath:/db/changelog/tech/db.changelog-tech.yaml"
    return liquibase
  }
}