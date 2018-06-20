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
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*

@Aspect
@Component
class RepositoryCacheAspect {
    companion object : KLogging()

    private val repositories = mutableMapOf<Class<*>, MutableMap<Method, MutableMap<Number, Any>>>()

    @Pointcut("within(@CacheableRepository *)")
    fun cacheableBeanPointcut() {}

    @Pointcut("execution(public * *.get*(..))")
    fun publicGetMethod() {}

    @Pointcut("cacheableBeanPointcut() && publicGetMethod()")
    fun cacheableGetPointcut() {}

    @Pointcut("@annotation(InvalidateCache) && execution(public * *(..))")
    fun publicInvalidatePointcut() {}

    @Around("cacheableGetPointcut()")
    fun around(jp: ProceedingJoinPoint): Any? {
        val signature = jp.signature as MethodSignature
        val type = signature.declaringType
        val method = signature.method

        logger.trace { "Invoking cacheable call for ${type.simpleName}.${method.name}" }
        val repo = repositories.computeIfAbsent(type, { mutableMapOf() })
        val meth = repo.computeIfAbsent(method, { mutableMapOf() })

        val hash = Arrays.hashCode(jp.args)

        if (meth.containsKey(hash)) {
            val res = meth[hash]
            logger.trace { "Return cached value is $res" }
            return res
        }
        val res = jp.proceed()

        logger.trace { "Save new value to cache and return after proceed $res" }
        meth[hash] = res
        return res
    }

    @After("publicInvalidatePointcut()")
    fun afterModification(jp: JoinPoint) {
        val type = jp.signature.declaringType
        logger.trace { "Reset cache for ${type.simpleName}" }
        repositories.remove(type)
    }
}