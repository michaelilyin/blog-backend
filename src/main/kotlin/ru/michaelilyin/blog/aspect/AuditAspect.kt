package ru.michaelilyin.blog.aspect

import mu.KLogger
import mu.KLogging
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.annotation.Configuration
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import java.lang.reflect.Method

@Aspect
@Configuration
class AuditAspect {

    companion object : KLogging() {

        private val loggers = mutableMapOf<Class<*>, KLogger>()

        fun getLogger(type: Class<*>): KLogger {
            return loggers.computeIfAbsent(type) {
                KotlinLogging.logger("ru.michaelilyin.blog.audit.${it.simpleName}")
            }
        }
    }

    @Pointcut("@annotation(ru.michaelilyin.blog.annotations.audit.Audit) && execution(* *(..))")
    fun annotationPointcut() {}

    @Around("annotationPointcut()")
    fun around(jp: ProceedingJoinPoint): Any? {
        val signature = jp.signature as MethodSignature
        val method = signature.method
        val type = signature.declaringType
        val audit = method.getAnnotation(Audit::class.java)
        val level = audit.level
        val typeLogger = getLogger(type)
        if (level == AuditLevel.INFO) {
            typeLogger.info { beforeMessage(signature, method, jp) }
        } else if (level == AuditLevel.DEBUG) {
            typeLogger.debug { beforeMessage(signature, method, jp) }
        }
        try {
            val res = jp.proceed()
            if (level == AuditLevel.INFO) {
                typeLogger.info { afterMessage(signature, method, res) }
            } else if (level == AuditLevel.DEBUG) {
                typeLogger.debug { afterMessage(signature, method, res) }
            }
            return res
        } catch (e: Exception) {
            typeLogger.warn(e) { exceptionMessage(signature, method, jp, e) }
            throw e
        }
    }

    private fun beforeMessage(signature: MethodSignature, method: Method, jp: ProceedingJoinPoint): String {
        return "invocation of ${signature.declaringType.simpleName}.${method.name} " +
                "started with arguments (${jp.args.joinToString()})"
    }

    private fun afterMessage(signature: MethodSignature, method: Method, res: Any?): String {
        return "invocation of ${signature.declaringType.simpleName}.${method.name} " +
                "finished with result (${res})"
    }

    private fun exceptionMessage(signature: MethodSignature, method: Method, jp: ProceedingJoinPoint, e: Exception): String {
        return "invocation of ${signature.declaringType.simpleName}.${method.name} " +
                "with arguments (${jp.args.joinToString()}) " +
                "completed with exception: ${e.message}"
    }
}