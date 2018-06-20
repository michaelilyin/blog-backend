package ru.michaelilyin.blog.service

import ru.michaelilyin.blog.dto.UserContextDTO

interface UserContextService {
    fun getUserContext(): UserContextDTO?
}