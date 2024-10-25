package com.knu.mockin.service.util

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.entity.UserWithKeyPair
import com.knu.mockin.model.enum.Constant.MOCK
import com.knu.mockin.model.enum.Constant.REAL
import com.knu.mockin.util.RedisUtil
import com.knu.mockin.util.tag

object ServiceUtil {
    fun createHeader(user: UserWithKeyPair, tradeId: String, isReal:Boolean = false) = KISOverSeaRequestHeaderDto(
        authorization = "Bearer ${RedisUtil.getToken(user.email tag if (isReal) REAL else MOCK)}",
        appKey = user.appKey,
        appSecret = user.appSecret,
        transactionId = tradeId
    )
}