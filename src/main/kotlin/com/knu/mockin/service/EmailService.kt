package com.knu.mockin.service

import com.knu.mockin.config.RedisConfig
import com.knu.mockin.exeption.CustomException
import com.knu.mockin.exeption.ErrorCode
import com.knu.mockin.model.dto.request.login.EmailCheckRequestDto
import com.knu.mockin.model.dto.response.SimpleMessageResponseDto
import com.knu.mockin.model.entity.User
import com.knu.mockin.repository.UserRepository
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.ValueOperations
import java.util.concurrent.TimeUnit

@Service
class EmailService(
    private val javaMailSender: JavaMailSender,
    private val redisConfig: RedisConfig,
    private val userRepository: UserRepository
) {

    private var authNumber: Int = 0

    @Value("\${spring.mail.username}")
    lateinit var serviceName: String

    /* 랜덤 인증번호 생성 */
    private fun makeRandomNum() {
        authNumber = (100000..999999).random() // 6자리 랜덤 숫자 생성
    }

    /* 이메일 전송 */
    private fun mailSend(setFrom: String, toMail: String, title: String, content: String) {
        val message: MimeMessage = javaMailSender.createMimeMessage()
        try {
            val helper = MimeMessageHelper(message, true, "utf-8")
            helper.setFrom(setFrom) // service name
            helper.setTo(toMail) // customer email
            helper.setSubject(title) // email title
            helper.setText(content, true) // content, html: true
            javaMailSender.send(message)
        } catch (e: MessagingException) {
            e.printStackTrace() // 에러 출력
        }
        // redis에 3분 동안 이메일과 인증 코드 저장
        val valOperations: ValueOperations<String, String> = redisConfig.redisTemplate().opsForValue()
        valOperations.set(toMail, authNumber.toString(), 180, TimeUnit.SECONDS)
    }

    /* 이메일 작성 */
    suspend fun joinEmail(email: String, password: String, name: String): SimpleMessageResponseDto {
        makeRandomNum()
        val customerMail = email
        val title = "회원 가입을 위한 이메일입니다!"
        val content =
            "이메일을 인증하기 위한 절차입니다.<br><br>" +
                    "인증 번호는 ${authNumber}입니다.<br>" +
                    "회원 가입 폼에 해당 번호를 입력해주세요."
        print("비밀번호@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@: ${password}")
        mailSend(serviceName, customerMail, title, content)
        userRepository.save(
            User(
                email= email,
                password = password,
                name = name,
            )
        ).awaitSingleOrNull()
        return SimpleMessageResponseDto("이메일을 보냈습니다~")
    }

    suspend fun checkAuthNum(emailCheckRequestDto: EmailCheckRequestDto): SimpleMessageResponseDto {
        val valOperations: ValueOperations<String, String> = redisConfig.redisTemplate().opsForValue()
        val code = valOperations.get(emailCheckRequestDto.email)

        if (code == emailCheckRequestDto.authNum) {
            // 이메일 인증 성공
            val user = userRepository.findByEmail(emailCheckRequestDto.email).awaitSingleOrNull()

            if (user != null) {
                print("email: ${user.email}")
                userRepository.verifyEmailByEmail(user.email).awaitSingleOrNull()
                return SimpleMessageResponseDto("이메일 인증이 완료되었습니다.")
            } else {
                throw CustomException(ErrorCode.USER_NOT_FOUND)
            }
        } else {
            // 이메일 인증 실패
            return SimpleMessageResponseDto("인증 실패")
        }
    }

}