package com.knu.mockin.service

import com.knu.mockin.kisclient.KISBasicClient
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisrequest.basic.KISCurrentPriceRequestParameterDto
import com.knu.mockin.model.dto.kisrequest.basic.KISTermPriceRequestParameterDto
import com.knu.mockin.model.dto.kisresponse.basic.KISCurrentPriceResponseDto
import com.knu.mockin.model.dto.kisresponse.basic.KISTermPriceResponseDto
import com.knu.mockin.model.enum.TradeId
import com.knu.mockin.repository.UserRepository
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service

@Service
class BasicService (
        private val kisBasicClient: KISBasicClient,
        private val userRepository: UserRepository
) {
    suspend fun getCurrentPrice(): KISCurrentPriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.CURRENT_PRICE) 
        )

        
        val requestParameter = KISCurrentPriceRequestParameterDto(
                
                EXCD = "NAS",
                SYMB = "TSLA"
        )

        
        return kisBasicClient.getCurrentPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()
    }

    suspend fun getTermPrice(): KISTermPriceResponseDto {
        val user = userRepository.findById(1).awaitFirst()
        
        val kisOverSeaRequestHeaderDto = KISOverSeaRequestHeaderDto(
                authorization = "Bearer ${user.token}",
                appKey = user.appKey,
                appSecret = user.appSecret,
                transactionId = TradeId.getTradeIdByEnum(TradeId.TERM_PRICE) 
        )

        
        val requestParameter = KISTermPriceRequestParameterDto(
                AUTH = "",  
                EXCD = "NAS",
                SYMB = "TSLA",
                GUBN = "0",    
                BYMD = "",     
                MODP = "0",    
                KEYB = ""
        )

        
        return kisBasicClient.getTermPrice(kisOverSeaRequestHeaderDto, requestParameter).awaitSingle()

    }



}