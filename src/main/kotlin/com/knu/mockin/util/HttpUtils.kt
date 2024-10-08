package com.knu.mockin.util

import com.fasterxml.jackson.annotation.JsonProperty
import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.model.dto.kisheader.response.KISOverSeaResponseHeaderDto
import org.springframework.http.HttpHeaders
import org.springframework.web.util.UriComponentsBuilder
import kotlin.reflect.full.memberProperties

object HttpUtils {
    fun addHeaders(headers: HttpHeaders, headerDto: KISOverSeaRequestHeaderDto) {
        headers["content-type"] = headerDto.contentType
        headers["authorization"] = headerDto.authorization
        headers["appkey"] = headerDto.appKey
        headers["appsecret"] = headerDto.appSecret
        headerDto.personalSecKey?.let { headers["personalseckey"] = it }
        headers["tr_id"] = headerDto.transactionId
        headerDto.transactionContinuation?.let { headers["tr_cont"] = it }
        headerDto.customerType?.let { headers["custtype"] = it }
        headerDto.sequenceNumber?.let { headers["seq_no"] = it }
        headerDto.macAddress?.let { headers["mac_address"] = it }
        headerDto.phoneNumber?.let { headers["phone_number"] = it }
        headerDto.ipAddress?.let { headers["ip_addr"] = it }
        headerDto.hashKey?.let { headers["hashkey"] = it }
        headerDto.globalUid?.let { headers["gt_uid"] = it }
    }

    fun extractHeaderDto(headers: HttpHeaders): KISOverSeaResponseHeaderDto {
        return KISOverSeaResponseHeaderDto(
            contentType = headers.getFirst("content-type") ?: "",
            transactionId = headers.getFirst("tr_id") ?: "",
            transactionContinuation = headers.getFirst("tr_cont") ?: "",
            globalUid = headers.getFirst("gt_uid") ?: ""
        )
    }

    fun <T : Any> buildUri(targetUrl: String, requestDto: T): String {
        val uriBuilder = UriComponentsBuilder.fromUriString(targetUrl)
        val properties = requestDto::class.memberProperties

        requestDto::class.java.constructors[0].parameters.forEach { prop ->
            val key = parseJsonProperty(prop.annotations[0].toString())
            val value = properties.find { it.name == prop.name }
            uriBuilder.queryParam(key, value?.call(requestDto))
        }

        return uriBuilder.toUriString()
    }

    private fun parseJsonProperty(input: String): String{
        val startIndex = input.indexOf("value=\"") + "value=\"".length
        val endIndex = input.indexOf("\"", startIndex)

        return input.substring(startIndex, endIndex)
    }
}