package com.knu.mockin.aspect

import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Aspect
@Component
class SecurityAspect {
    @Pointcut("execution(* com.knu.mockin.controller..*(..))")
    fun controllerMethods() {}

    @Pointcut("execution(* com.knu.mockin.controller.AccountController.*(..))")
    fun accountControllerMethods() {}

    @Pointcut("execution(* com.knu.mockin.controller.HealthCheckController.*(..))")
    fun healthCheckControllerMethods() {}

    @Pointcut("controllerMethods() && !accountControllerMethods() && !healthCheckControllerMethods()")
    fun nonAccountControllerMethods() {}

    @Before("nonAccountControllerMethods()")
    fun injectEmailIntoDto(joinPoint: JoinPoint) {
        val args = joinPoint.args

        val authentication: Authentication = SecurityContextHolder.getContext().authentication
            ?: throw CustomException(ErrorCode.USER_NOT_FOUND)
        val email = authentication.name


    }

}