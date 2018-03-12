package ru.michaelilyin.blog.service

import ru.michaelilyin.blog.dto.PermissionDTO
import ru.michaelilyin.blog.dto.RoleDTO

interface PermissionService {
    fun getPermissions(search: String?): List<PermissionDTO>
    fun getPermissionsOfRole(id: String): List<PermissionDTO>
}