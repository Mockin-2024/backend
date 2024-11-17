package com.knu.mockin.service.login

import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.model.dto.request.login.Jwt
import com.knu.mockin.model.dto.request.login.LoginRequestDto
import com.knu.mockin.model.dto.request.login.SignupRequestDto
import com.knu.mockin.model.dto.request.login.TokenValidationRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.model.enum.Constant.JWT
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.BearerToken
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val encoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository
) {

    // 유저 저장
    suspend fun createUser(signupRequestDto: SignupRequestDto): SimpleMessageResponseDto {
        val existingUser = userRepository.findByEmail(signupRequestDto.email).awaitSingleOrNull()

        if (existingUser != null) {
            throw CustomException(ErrorCode.ALREADY_REGISTERED)
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

        user?.let {
            if (encoder.matches(loginRequestDto.password, it.password)) {
                val storedToken = RedisUtil.getToken(user.email tag JWT)

                if (storedToken != null) {
                    return Jwt(BearerToken(storedToken).value)
                }

                return Jwt(jwtUtil.generate(it.email).value)
            }
        }

        throw CustomException(ErrorCode.INVALID_LOGIN)
    }

    suspend fun validateToken(
        requestDto: TokenValidationRequestDto
    ): Jwt {
        val storedToken = RedisUtil.getToken(requestDto.email tag JWT)

        if (requestDto.token == storedToken) {
            return Jwt(jwtUtil.generate(requestDto.email).value)
        }

        throw CustomException(ErrorCode.TOKEN_EXPIRED)
    }
}
