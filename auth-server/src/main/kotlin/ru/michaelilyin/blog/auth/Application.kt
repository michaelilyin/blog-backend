package ru.michaelilyin.blog.auth

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.SessionAttributes
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.core.annotation.Order
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.core.io.ClassPathResource
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import java.security.KeyPair
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer







@Controller
@Configuration
@ComponentScan
@EnableAutoConfiguration
@SessionAttributes("authorizationRequest")
@SpringBootApplication
class Application : WebMvcConfigurer {

    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/login").setViewName("login")
        registry.addViewController("/oauth/confirm_access").setViewName("authorize")
    }

}

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 5)
public class LoginConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    override fun configure(http: HttpSecurity) {
        http.formLogin()
                .loginPage("/login").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.parentAuthenticationManager(authenticationManager)
    }
}

@Configuration
@EnableAuthorizationServer
class OAuth2Config : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        val keyPair = KeyStoreKeyFactory(ClassPathResource("keystore.jks"), "foobar".toCharArray()).getKeyPair("test")
        converter.setKeyPair(keyPair)
        return converter
    }

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients!!.inMemory()
                .withClient("acme")
                .secret("acmesecret")
                .authorizedGrantTypes("authorization_code", "refresh_token", "password")
                .scopes("openid")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
    }

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}