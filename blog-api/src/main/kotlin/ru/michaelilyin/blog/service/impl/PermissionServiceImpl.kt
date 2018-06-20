package ru.michaelilyin.blog.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.dao.AuthClientRepository
import ru.michaelilyin.blog.dao.RoleRepository
import ru.michaelilyin.blog.dto.PermissionDTO
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.dto.mapper.PermissionDTOMapper
import ru.michaelilyin.blog.dto.mapper.RoleDTOMapper
import ru.michaelilyin.blog.service.PermissionService

@Service
class PermissionServiceImpl @Autowired() constructor(
        private val roleRepository: RoleRepository,
        private val authClientRepository: AuthClientRepository
): PermissionService {

    @Audit
    override fun getPermissions(search: String?): List<PermissionDTO> {
        val permissions = mutableListOf<PermissionDTO>()
        val realmRoles = roleRepository.getRoles()
        permissions.addAll(
                realmRoles.asSequence()
                        .filter { !(it.composite ?: false) }
                        .map { PermissionDTOMapper.toDTO(it) }
        )
        val clients = authClientRepository.getClients()
        clients.forEach { client ->
            val clientRoles = roleRepository.getRoles(client.id)
            permissions.addAll(
                    clientRoles.asSequence()
                            .filter { !(it.composite ?: false) }
                            .map { PermissionDTOMapper.toDTO(it) }
            )
        }
        return applyFilter(permissions, search)
    }

    @Audit
    override fun getPermissionsOfRole(id: String): List<PermissionDTO> {
        val nestedRoles = roleRepository.getNestedRoles(id)
        return nestedRoles.asSequence()
                .filter { !(it.composite ?: false) }
                .map { PermissionDTOMapper.toDTO(it) }
                .toList()
    }

    private fun applyFilter(roles: MutableList<PermissionDTO>, search: String?): List<PermissionDTO> {
        return roles
                .filter {
                    if (search == null) {
                        true
                    } else {
                        it.name.contains(search, true)
                                || it.description?.contains(search, true) ?: false
                    }
                }
    }
}