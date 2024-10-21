package com.knu.mockin.security

import com.knu.mockin.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import reactor.core.publisher.Mono

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@Order(1)
class SecurityConfig {

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
            .exceptionHandling { exceptions ->
                exceptions.authenticationEntryPoint { exchange, _ ->
                    Mono.fromRunnable {
                        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                        exchange.response.headers.set(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
                    }
                }
            }
            .authorizeExchange { authorize ->
                authorize
                    .pathMatchers(HttpMethod.POST, "/auth/**").permitAll()
                    .pathMatchers("/docs/**").permitAll()
                    .anyExchange().authenticated()
            }
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .csrf { it.disable() }

        return http.build()
    }

    @Bean
    fun userDetailsService(encoder: PasswordEncoder, userRepository: UserRepository): CustomUserDetailsService {
        return CustomUserDetailsService(userRepository, encoder)
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()



}