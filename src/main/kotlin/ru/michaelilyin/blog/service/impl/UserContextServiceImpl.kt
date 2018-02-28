package ru.michaelilyin.blog.service.impl

import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.annotations.notification.EventType
import ru.michaelilyin.blog.annotations.notification.NotifyChanges
import ru.michaelilyin.blog.dto.UserContextDTO
import ru.michaelilyin.blog.service.AuthenticationFacade
import ru.michaelilyin.blog.service.UserContextService

@Service
class UserContextServiceImpl @Autowired() constructor(
        private val authenticationFacade: AuthenticationFacade
) : UserContextService {

    @Audit(AuditLevel.INFO)
    override fun getUserContext(): UserContextDTO? {
        val auth = authenticationFacade.getAuthentication()
        if (auth.isAuthenticated) {
            if (auth is AnonymousAuthenticationToken) {
                return null
            }
            val token = auth as KeycloakAuthenticationToken
            val details = token.details as SimpleKeycloakAccount
            val accessToken = details.keycloakSecurityContext.token

            val roles = mutableListOf<String>()
            roles.addAll(accessToken.realmAccess.roles)
            accessToken.resourceAccess.forEach {
                roles.addAll(it.value.roles)
            }

            val authorities = auth.authorities.map { it.authority }

            val name = details.principal.name
            return UserContextDTO(name, roles, authorities)
        } else {
            return null
        }
    }

}