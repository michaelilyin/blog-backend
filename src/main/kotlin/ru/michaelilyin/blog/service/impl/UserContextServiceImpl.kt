package ru.michaelilyin.blog.service.impl

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.dto.UserContextDTO
import ru.michaelilyin.blog.service.AuthenticationFacade
import ru.michaelilyin.blog.service.UserContextService

@Service
class UserContextServiceImpl @Autowired() constructor(
        private val authenticationFacade: AuthenticationFacade
) : UserContextService {

    @Audit(false)
    override fun getUserContext(): UserContextDTO? {
        val auth = authenticationFacade.getAuthentication()
        if (auth.isAuthenticated) {
            if (auth is AnonymousAuthenticationToken) {
                return null
            }
            val token = auth as KeycloakAuthenticationToken
            val details = token.details as SimpleKeycloakAccount
            val name = details.principal.name
            return UserContextDTO(name)
        } else {
            return null
        }
    }

}