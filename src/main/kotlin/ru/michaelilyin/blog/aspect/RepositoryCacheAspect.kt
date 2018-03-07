package ru.michaelilyin.blog.aspect

import mu.KLogger
import mu.KLogging
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class RepositoryCacheAspect {
    companion object : KLogging()

    private val repositories = mutableMapOf<Class<*>, MutableMap<Array<Any>, Any>>()

    @Pointcut("within(@ru.michaelilyin.blog.annotations.cache.repository.CacheableRepository)")
    fun cacheableBeanPointcut() {}

    @Pointcut("within(@ru.michaelilyin.blog.annotations.cache.repository.InvalidateCache)")
    fun invalidateCachePointcut() {}

    @Pointcut("execution(public * *.get*(..))")
    fun publicGetMethod() {}

    @Pointcut("cacheableBeanPointcut() && publicGetMethod()")
    fun cacheableGetPointcut() {}

    @Pointcut("execution(public * *.create*(..)) || execution(public * *.udpate*(..)) || execution(public * *.delete*(..))")
    fun publicModifyMethod() {}

    @Pointcut("invalidateCachePointcut() && publicModifyMethod()")
    fun publicInvalidatePointcut() {}

    @Around("cacheableGetPointcut()")
    fun around(jp: ProceedingJoinPoint): Any? {
        val type = jp.signature.declaringType
        val repo = repositories.computeIfAbsent(type, { mutableMapOf() })
        if (repo.containsKey(jp.args)) {
            return repo[jp.args]
        }
        val res = jp.proceed()
        repo[jp.args] = res
        return res
    }

    @After("publicInvalidatePointcut()")
    fun afterModification(jp: JoinPoint) {
        val type = jp.signature.declaringType
        repositories.remove(type)
    }
}