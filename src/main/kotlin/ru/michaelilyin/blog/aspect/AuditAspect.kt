package ru.michaelilyin.blog.aspect

import mu.KLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.context.annotation.Configuration
import ru.michaelilyin.blog.annotations.audit.Audit

@Aspect
@Configuration
class AuditAspect {

    companion object : KLogging()

    @Pointcut("@annotation(ru.michaelilyin.blog.annotations.audit.Audit) && execution(* *(..))")
    fun annotationPointcut() {}

    @Around("annotationPointcut()")
    fun around(jp: ProceedingJoinPoint): Any? {
        val signature = jp.signature as MethodSignature
        val method = signature.method
        val audit = method.getAnnotation(Audit::class.java)
        val detailed = !audit.errorOnly
        if (detailed) {
            logger.info {
                "invocation of ${signature.declaringType.simpleName}.${method.name} " +
                        "started with arguments (${jp.args.joinToString()})"
            }
        }
        try {
            val res = jp.proceed()
            if (detailed) {
                logger.info {
                    "invocation of ${signature.declaringType.simpleName}.${method.name} " +
                            "finished with result (${res})"
                }
            }
            return res
        } catch (e: Exception) {
            logger.warn(e) {
                "invocation of ${signature.declaringType.simpleName}.${method.name} " +
                        "with arguments (${jp.args.joinToString()}) " +
                        "completed with exception: ${e.message}"
            }
            throw e
        }
    }
}