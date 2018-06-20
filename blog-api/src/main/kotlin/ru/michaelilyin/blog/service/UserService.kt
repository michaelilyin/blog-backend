package ru.michaelilyin.blog.service

import org.springframework.stereotype.Service
import ru.michaelilyin.blog.dto.UserDTO

interface UserService {
    fun getUsers(search: String?): List<UserDTO>
    fun findUser(id: Long): UserDTO;
}