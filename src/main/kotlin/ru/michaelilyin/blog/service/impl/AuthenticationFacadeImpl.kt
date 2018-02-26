package ru.michaelilyin.blog.service.impl

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.service.AuthenticationFacade

@Service
class AuthenticationFacadeImpl: AuthenticationFacade {
    override fun getAuthentication(): Authentication {
        return SecurityContextHolder.getContext().authentication
    }
}