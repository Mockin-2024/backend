package com.knu.mockin.controller.quotations.analysis

import com.knu.mockin.model.dto.kisresponse.quotations.analysis.KISNewsTitleResponseDto
import com.knu.mockin.model.dto.request.quotations.analysis.real.NewsTitleRequestParameterDto
import com.knu.mockin.service.quotations.analysis.AnalysisRealService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quotations/analysis")
class AnalysisRealController(
    private val analysisRealService: AnalysisRealService
) {

    @GetMapping("/news-title")
    suspend fun getNewsTitle(
        @ModelAttribute newsTitleRequestParameterDto: NewsTitleRequestParameterDto,
        authentication: Authentication
    ): ResponseEntity<KISNewsTitleResponseDto> {
        val result = analysisRealService.getNewsTitle(newsTitleRequestParameterDto, authentication.name)

        return ResponseEntity.ok(result)
    }

}