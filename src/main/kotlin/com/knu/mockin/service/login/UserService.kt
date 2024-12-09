package com.knu.mockin.service.login

import com.knu.mockin.exception.CustomException
import com.knu.mockin.exception.ErrorCode
import com.knu.mockin.model.dto.response.LoginResponseDto
import com.knu.mockin.model.dto.request.login.LoginRequestDto
import com.knu.mockin.model.dto.request.login.SignupRequestDto
import com.knu.mockin.model.dto.request.login.TokenValidationRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.dto.response.buildLoginResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.model.enum.Constant.JWT
import com.knu.mockin.repository.UserRepository
import com.knu.mockin.security.JwtUtil
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag
import kotlinx.coroutines.reactor.awaitSingle
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

    suspend fun loginUser(loginRequestDto: LoginRequestDto): LoginResponseDto {
        val userInfo = userRepository.findUserInfoByEmail(loginRequestDto.email).awaitSingleOrNull()
        println(userInfo)
        userInfo?.let {
            if (encoder.matches(loginRequestDto.password, it.password)) {
                val storedToken = RedisUtil.getToken(userInfo.email tag JWT)

                if (storedToken != null) {
                    return buildLoginResponseDto(storedToken, userInfo)
                }

                return buildLoginResponseDto(jwtUtil.generate(it.email).value, userInfo)
            }
        }

        throw CustomException(ErrorCode.INVALID_LOGIN)
    }

    suspend fun validateToken(
        requestDto: TokenValidationRequestDto
    ): LoginResponseDto {
        val userInfo = userRepository.findUserInfoByEmail(requestDto.email).awaitSingle()
        val storedToken = RedisUtil.getToken(requestDto.email tag JWT)

        if (requestDto.token == storedToken) {
            return buildLoginResponseDto(jwtUtil.generate(requestDto.email).value, userInfo)
        }

        throw CustomException(ErrorCode.TOKEN_EXPIRED)
    }
}
