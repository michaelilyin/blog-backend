package ru.michaelilyin.blog.config

import mu.KLogging
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import javax.annotation.PostConstruct


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackageClasses = arrayOf(KeycloakSecurityComponents::class))
class SecurityConfig : KeycloakWebSecurityConfigurerAdapter() {

    companion object : KLogging()

    init {
        logger.info { "Security configuration created" }
    }

    @Autowired
    private lateinit var keycloakClientRequestFactory: KeycloakClientRequestFactory

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(keycloakAuthenticationProvider)
        logger.info { "Authentication configured globally" }
    }

    @Bean
    fun keycloakConfigResolver(): KeycloakSpringBootConfigResolver {
        logger.info { "Resolver created" }
        return KeycloakSpringBootConfigResolver()
    }

    @Bean
    override fun sessionAuthenticationStrategy(): SessionAuthenticationStrategy {
        logger.info { "Session strategy initialized" }
        return RegisterSessionAuthenticationStrategy(SessionRegistryImpl())
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    fun keycloakRestTemplate(): KeycloakRestTemplate {
        logger.info { "Rest template produced" }
        return KeycloakRestTemplate(keycloakClientRequestFactory)
    }

    override fun configure(http: HttpSecurity) {
        super.configure(http)
        logger.info { "Constraints specified" }
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().ignoringAntMatchers("/graphql")
//                .and()
//                .logout()
//                .addLogoutHandler(keycloakLogoutHandler())
//                .logoutUrl("/sso/logout").permitAll()
//                .logoutSuccessUrl("/")
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
    }
}
