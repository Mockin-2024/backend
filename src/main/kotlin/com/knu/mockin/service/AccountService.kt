package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOauth2Client
import com.knu.mockin.logging.model.LogAPIEntry
import com.knu.mockin.logging.utils.LogUtil
import com.knu.mockin.model.dto.kisrequest.oauth.KISApprovalRequestDto
import com.knu.mockin.model.dto.kisrequest.oauth.KISTokenRequestDto
import com.knu.mockin.model.dto.response.AccessTokenAPIResponseDto
import com.knu.mockin.model.dto.response.ApprovalKeyResponseDto
import com.knu.mockin.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AccountService(
    private val kisOauth2Client: KISOauth2Client,
    private val userRepository: UserRepository
) {

    private val log = LoggerFactory.getLogger(AccountService::class.java)
    fun getApprovalKey(requestDto: KISApprovalRequestDto): Mono<ApprovalKeyResponseDto> {
        val result = kisOauth2Client.postApproval(requestDto)
        result.doOnNext{
            dto ->
            log.info("{}", LogUtil.toJson(LogAPIEntry(dto)))
        }.subscribe()
        return result
    }

    fun getAccessToken(requestDto: KISTokenRequestDto): Mono<AccessTokenAPIResponseDto> {
        return userRepository.findById(1)
            .flatMap { user ->
                kisOauth2Client.postTokenP(requestDto)
                    .flatMap { dto ->
                        user.token = dto.accessToken
                        userRepository.save(user)
                            .then(Mono.just(dto))
                    }
            }
    }

}