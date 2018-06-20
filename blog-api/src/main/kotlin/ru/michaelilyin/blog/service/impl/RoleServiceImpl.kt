package ru.michaelilyin.blog.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.dao.AuthClientRepository
import ru.michaelilyin.blog.dao.RoleRepository
import ru.michaelilyin.blog.dao.UserRepository
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.dto.UserDTO
import ru.michaelilyin.blog.dto.mapper.RoleDTOMapper
import ru.michaelilyin.blog.model.KeycloakClient
import ru.michaelilyin.blog.model.KeycloakRole
import ru.michaelilyin.blog.service.RoleService
import ru.michaelilyin.blog.service.UserService

@Service
class RoleServiceImpl @Autowired() constructor(
        private val roleRepository: RoleRepository
) : RoleService {

    @Audit
    override fun getRoles(search: String?): List<RoleDTO> {
        val allRoles = mutableListOf<RoleDTO>()
        val realmRoles = roleRepository.getRoles()
        allRoles.addAll(
                realmRoles.asSequence()
                        .filter { it.composite ?: false }
                        .map { RoleDTOMapper.toDTO(it) }
        )
        return applyFilter(allRoles, search)
    }

    @Audit
    override fun getRolesOfUser(id: String): List<RoleDTO> {
        return roleRepository.getRolesOfUser(id).realmMappings.asSequence()
                .filter { it.composite ?: false }
                .map { RoleDTOMapper.toDTO(it) }
                .toList()
    }

    private fun applyFilter(roles: MutableList<RoleDTO>, search: String?): List<RoleDTO> {
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