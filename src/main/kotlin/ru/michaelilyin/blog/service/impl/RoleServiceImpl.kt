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
        val allRoles = mutableListOf<RoleDTO>()
        allRoles.addAll(rootRoles.asSequence().map { RoleDTO(
                it.id,
                it.name,
                "realm",
                it.description
        ) })

        val clients = authClientRepository.getClients()
        clients.forEach {client ->
            val clientRoles = roleRepository.getRoles(client.id)
            allRoles.addAll(clientRoles.asSequence().map {
                RoleDTO(
                        it.id,
                        it.name,
                        client.id,
                        it.description
                )
            })
        }

        return allRoles
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