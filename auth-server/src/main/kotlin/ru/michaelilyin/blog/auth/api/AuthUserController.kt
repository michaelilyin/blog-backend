package ru.michaelilyin.blog.auth.api

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
@RequestMapping("/")
class AuthUserController {

    /**
     * Return the principal identifying the logged in user
     * @param user
     * @return
     */
    @RequestMapping("/me")
    fun getCurrentLoggedInUser(user: Principal): Principal {
        return user
    }
}