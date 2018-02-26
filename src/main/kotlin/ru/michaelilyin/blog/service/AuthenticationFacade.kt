package ru.michaelilyin.blog.service

import org.springframework.security.core.Authentication

interface AuthenticationFacade {
    fun getAuthentication(): Authentication;
}