package ru.michaelilyin.blog.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.dao.AuthClientRepository
import ru.michaelilyin.blog.dao.RoleRepository
import ru.michaelilyin.blog.dao.UserRepository
import ru.michaelilyin.blog.dto.RoleDTO
import ru.michaelilyin.blog.dto.UserDTO
import ru.michaelilyin.blog.model.KeycloakRole
import ru.michaelilyin.blog.service.RoleService
import ru.michaelilyin.blog.service.UserService

@Service
class RoleServiceImpl @Autowired() constructor(
        private val roleRepository: RoleRepository,
        private val authClientRepository: AuthClientRepository
) : RoleService {

    @Audit
    override fun getRoles(search: String?): List<RoleDTO> {
        val rootRoles = roleRepository.getRoles()
        val allRoles = mutableListOf<KeycloakRole>()
        allRoles.addAll(rootRoles)

        val clients = authClientRepository.getClients()
        clients.forEach {
            val clientRoles = roleRepository.getRoles(it.id)
            allRoles.addAll(clientRoles)
        }

        return allRoles.asSequence()
                .filter {
                    if (search == null) {
                        true
                    } else {
                        it.name.contains(search, true)
                                || it.description?.contains(search, true) ?: false
                    }
                }
                .map {
                    RoleDTO(
                            it.id,
                            it.name,
                            it.description
                    )
                }
                .toList()
    }
}