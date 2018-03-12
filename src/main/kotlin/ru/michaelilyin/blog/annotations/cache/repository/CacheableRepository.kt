package ru.michaelilyin.blog.annotations.cache.repository

import org.springframework.stereotype.Repository

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
@Repository
annotation class CacheableRepository()