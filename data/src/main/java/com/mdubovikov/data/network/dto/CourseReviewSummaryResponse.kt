package com.mdubovikov.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseReviewSummaryResponse(
    @SerialName("course-review-summaries")
    val courseReviewSummaries: List<ReviewSummaryDto>
)

@Serializable
data class ReviewSummaryDto(
    val id: Int,
    val course: Int,
    val average: Double,
    val count: Int
)