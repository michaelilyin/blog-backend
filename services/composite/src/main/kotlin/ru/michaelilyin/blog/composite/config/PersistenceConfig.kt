package ru.michaelilyin.blog.composite.config

import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import ru.michaelilyin.blog.tech.annotations.datasource.TechDataSource
import javax.sql.DataSource

@Configuration
class PersistenceConfig {
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource")
  fun dataSourceProperties(): DataSourceProperties {
    return DataSourceProperties()
  }

  @Bean
  @Primary
  fun dataSource(props: DataSourceProperties): DataSource {
    return props.initializeDataSourceBuilder().build()
  }

  @Bean
  @Primary
  fun jdbc(ds: DataSource): JdbcTemplate {
    return JdbcTemplate(ds)
  }

  @Bean
  @Primary
  fun namedJdbc(ds: DataSource): NamedParameterJdbcTemplate {
    return NamedParameterJdbcTemplate(ds)
  }

  @Bean(name = ["liquibase-master"])
  @Primary
  fun liquibase(ds: DataSource): SpringLiquibase {
    val liquibase = SpringLiquibase()
    liquibase.dataSource = ds
    liquibase.changeLog = "classpath:/db/changelog/db.changelog-master.yaml"
    return liquibase
  }
}