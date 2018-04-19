package ru.michaelilyin.blog.service.impl

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
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
            throw NotImplementedError()
        } else {
            return null
        }
    }
}