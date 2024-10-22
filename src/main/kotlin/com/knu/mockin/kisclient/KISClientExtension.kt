package com.knu.mockin.kisclient

import com.knu.mockin.model.dto.kisheader.request.KISOverSeaRequestHeaderDto
import com.knu.mockin.util.HttpUtils
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

fun <T: Any, R:Any> WebClient.postWithBody(
    uri: String,
    headerDto: KISOverSeaRequestHeaderDto? = null,
    bodyDto: T,
    responseType: Class<R>
): Mono<R> {
    return this.post()
        .uri(uri)
        .headers {
            if (headerDto != null) {
                HttpUtils.addHeaders(it, headerDto)
            }
        }
        .bodyValue(bodyDto)
        .retrieve()
        .bodyToMono(responseType)
}

fun <T: Any> WebClient.getWithParams(
    uri: String,
    headerDto: KISOverSeaRequestHeaderDto,
    responseType: Class<T>
): Mono<T> {
    return this.get()
        .uri(uri)
        .headers { HttpUtils.addHeaders(it, headerDto) }
        .retrieve()
        .bodyToMono(responseType)
}