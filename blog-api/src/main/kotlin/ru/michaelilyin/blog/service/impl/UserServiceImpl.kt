package ru.michaelilyin.blog.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.dao.UserRepository
import ru.michaelilyin.blog.dto.UserDTO
import ru.michaelilyin.blog.service.UserService

@Service
class UserServiceImpl @Autowired() constructor(
        private val userRepository: UserRepository
) : UserService {

    @Audit
    override fun getUsers(search: String?): List<UserDTO> {
        return userRepository.getUsers(search).map {
            UserDTO(
                    it.id,
                    it.username,
                    it.email,
                    it.firstName,
                    it.lastName
            )
        }
    }

    override fun findUser(id: Long): UserDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}