package ru.michaelilyin.blog.aspect

import mu.KLogger
import mu.KLogging
import mu.KotlinLogging
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import ru.michaelilyin.blog.annotations.audit.Audit
import ru.michaelilyin.blog.annotations.audit.AuditLevel
import ru.michaelilyin.blog.service.AuditService
import java.lang.reflect.Method

@Aspect
@Configuration
class AuditAspect @Autowired() constructor(
        private val auditService: AuditService
) {

    companion object : KLogging() {

        private val loggers = mutableMapOf<Class<*>, KLogger>()

        fun getLogger(type: Class<*>): KLogger {
            return loggers.computeIfAbsent(type) {
                KotlinLogging.logger("ru.michaelilyin.blog.audit.${it.simpleName}")
            }
        }
    }

    @Pointcut("@annotation(ru.michaelilyin.blog.annotations.audit.Audit) && execution(* *(..)) && @annotation(audit)")
    fun annotationPointcut() {}

    @Around("annotationPointcut()")
    fun around(jp: ProceedingJoinPoint, audit: Audit): Any? {
        val signature = jp.signature as MethodSignature
        val method = signature.method
        val type = signature.declaringType
        val level = audit.level
        val typeLogger = getLogger(type)
        val tag = "${signature.declaringType.simpleName}.${method.name}"

        try {
            auditBefore(tag, jp, level, typeLogger)
            val res = jp.proceed()
            auditAfter(tag, res, level, typeLogger)
            return res
        } catch (e: Exception) {
            auditException(tag, jp, e, typeLogger)
            throw e
        }
    }

    private fun auditException(tag: String, jp: ProceedingJoinPoint, e: Exception, typeLogger: KLogger) {
        val exceptionMessage = exceptionMessage(tag, jp, e)
        typeLogger.warn(exceptionMessage, e)
        auditService.createAuditRecord(tag, AuditLevel.ERROR, exceptionMessage, e)
    }

    private fun auditAfter(tag: String, res: Any?, level: AuditLevel, typeLogger: KLogger) {
        val afterMessage = afterMessage(tag, res)
        if (level == AuditLevel.INFO) {
            typeLogger.info(afterMessage)
            auditService.createAuditRecord(tag, level, afterMessage)
        } else if (level == AuditLevel.DEBUG) {
            typeLogger.debug(afterMessage)
            auditService.createAuditRecord(tag, level, afterMessage)
        }
    }

    private fun auditBefore(tag: String, jp: ProceedingJoinPoint, level: AuditLevel, typeLogger: KLogger) {
        val beforeMessage = beforeMessage(tag, jp)
        if (level == AuditLevel.INFO) {
            typeLogger.info(beforeMessage)
            auditService.createAuditRecord(tag, level, beforeMessage)
        } else if (level == AuditLevel.DEBUG) {
            typeLogger.debug(beforeMessage)
            auditService.createAuditRecord(tag, level, beforeMessage)
        }
    }

    private fun beforeMessage(tag: String, jp: ProceedingJoinPoint): String {
        return "invocation of ${tag} " +
                "started with arguments (${jp.args.joinToString()})"
    }

    private fun afterMessage(tag: String, res: Any?): String {
        return "invocation of ${tag} " +
                "finished with result (${res})"
    }

    private fun exceptionMessage(tag: String, jp: ProceedingJoinPoint, e: Exception): String {
        return "invocation of ${tag} " +
                "with arguments (${jp.args.joinToString()}) " +
                "completed with exception: ${e.message}"
    }
}