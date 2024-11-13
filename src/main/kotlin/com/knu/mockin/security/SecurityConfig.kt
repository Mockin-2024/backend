package com.knu.mockin.security

import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.handler.JwtAccessDeniedHandler
import com.knu.mockin.security.handler.JwtAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@Order(1)
class SecurityConfig(
    val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint
) {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun springSecurityFilterChain(
        converter: JwtServerAuthenticationConverter,
        http: ServerHttpSecurity,
        authManager: JwtAuthenticationManager
    ): SecurityWebFilterChain {
        val filter = AuthenticationWebFilter(authManager)
        filter.setServerAuthenticationConverter(converter)


        http
            .authorizeExchange { authorize ->
                authorize
                    .pathMatchers(HttpMethod.POST, "/auth/**").permitAll()
                    .pathMatchers("/docs/**", "/health").permitAll()
                    .anyExchange().authenticated()
            }
            .cors { cors -> cors.disable() }
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }
            .exceptionHandling { exception ->
                exception.accessDeniedHandler(jwtAccessDeniedHandler)
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }

        return http.build()
    }

    @Bean
    fun userDetailsService(encoder: PasswordEncoder, userRepository: UserRepository): CustomUserDetailsService {
        return CustomUserDetailsService(userRepository, encoder)
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()



}