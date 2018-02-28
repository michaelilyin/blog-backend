package ru.michaelilyin.blog.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class RootController {

    @GetMapping
    fun get(): String {
        return ""
    }
}