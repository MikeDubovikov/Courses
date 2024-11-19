package com.mdubovikov.data.mappers

import com.mdubovikov.data.network.dto.ReviewSummaryDto
import com.mdubovikov.domain.catalog.entity.CourseReviewSummary
import java.util.Locale

fun ReviewSummaryDto.toCourseReviewSummary(): CourseReviewSummary = CourseReviewSummary(
    id = id,
    course = course,
    average = average.trimToTenths(),
    count = count
)

private fun Double.trimToTenths(): Double {
    return String.format(Locale.US, "%.1f", this).toDouble()
}