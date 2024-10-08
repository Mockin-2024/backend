package com.knu.mockin.service

import com.knu.mockin.kisclient.KISOverSeaClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.TestRequestParameter
import com.knu.mockin.model.dto.kisresponse.TestResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class TestService (
        private val kisOverSeaClient: KISOverSeaClient,
        private val userRepository: UserRepository
) {

    fun getTest(): Mono<TestResponseDto> {
        return userRepository.findById(1).flatMap { user ->
            // Request header 생성
            val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                    authorization = "Bearer ${user.token}",
                    appKey = user.appKey,
                    appSecret = user.appSecret,
                    transactionId = TradeId.TEST.name // 적절한 거래 ID로 수정
            )

            // 요청 파라미터 생성
            val requestParameter = TestRequestParameter(
                    fidCondMrktDivCode = "J",
                    fidInputIscd = "000660"
            )

            // KIS API 호출
            kisOverSeaClient.getTest(kisOverSeaRequestHeaderDto, requestParameter)
        }
    }

}