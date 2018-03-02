package ru.michaelilyin.blog.service.impl

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.dto.UserContextDTO
import ru.michaelilyin.blog.service.AuthenticationFacade

@Service
class AuthenticationFacadeImpl: AuthenticationFacade {
    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

    override fun getUserLogin(): String? {
        val auth = getAuthentication()
        if (auth.isAuthenticated) {
            if (auth is AnonymousAuthenticationToken) {
                return null
            }
            val token = auth as KeycloakAuthenticationToken
            val details = token.details as SimpleKeycloakAccount
            return details.principal.name
        } else {
            return null
        }
    }
}