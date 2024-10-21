package com.knu.mockin.security

import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
@Primary
class CustomUserDetailsService(
    private val userRepository: UserRepository,
    private val encode: PasswordEncoder
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return userRepository.findByEmail(username)
            .map { user ->
                org.springframework.security.core.userdetails.User.withUsername(user.email)
                    .password(encode.encode(user.password))
                    .roles(user.role)
                    .build()
            }
            .switchIfEmpty(Mono.error(UsernameNotFoundException("User not found with username: ${username}")))
    }
}