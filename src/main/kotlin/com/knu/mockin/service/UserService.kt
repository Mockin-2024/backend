package com.knu.mockin.service

import com.knu.mockin.model.dto.request.login.Jwt
import com.knu.mockin.model.dto.request.login.LoginRequestDto
import com.knu.mockin.model.dto.request.login.SignupRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtSupport
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@Service
class UserService(
    private val emailService: EmailService,
    private val encoder: PasswordEncoder,
    private val jwtSupport: JwtSupport,
    private val userRepository: UserRepository
) {

    // 유저 저장
    suspend fun createUser(signupRequestDto: SignupRequestDto): SimpleMessageResponseDto {
        val existingUser = userRepository.findByEmail(signupRequestDto.email).awaitSingleOrNull()

        if (existingUser != null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다.")
        }

        val encodedPassword = encoder.encode(signupRequestDto.password)

        userRepository.save(
            User(
                email= signupRequestDto.email,
                password = encodedPassword,
                name = signupRequestDto.name,
            )
        ).awaitSingleOrNull()

        return SimpleMessageResponseDto("회원가입이 완료되었습니다!")
    }

    suspend fun loginUser(loginRequestDto: LoginRequestDto): Jwt {
        val user = userRepository.findByEmail(loginRequestDto.email).awaitSingleOrNull()

        // 사용자 검증 및 비밀번호 비교
        user?.let {
            if (encoder.matches(loginRequestDto.password, it.password)) {
                // 이메일 인증 여부 확인
                if (!it.verified) {
                    throw ResponseStatusException(HttpStatus.FORBIDDEN, "이메일 인증이 완료되지 않았습니다.")
                }
                return Jwt(jwtSupport.generate(it.email).value) // JWT 발급
            }
        }

        throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다.")
    }

    // 이메일로 유저 찾기
    suspend fun findByEmail(email: String): Mono<User> {
        return userRepository.findByEmail(email)
    }

}
