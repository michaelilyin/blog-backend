package ru.michaelilyin.blog.service

import org.springframework.stereotype.Service
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.dto.UserDTO

interface RoleService {
    fun getRoles(search: String?): List<RoleDTO>
}