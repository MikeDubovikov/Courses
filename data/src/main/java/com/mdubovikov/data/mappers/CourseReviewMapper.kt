package com.mdubovikov.data.mappers

import android.annotation.SuppressLint
import com.mdubovikov.data.network.dto.ReviewSummaryDto
import com.mdubovikov.domain.catalog.entity.CourseReviewSummary

fun ReviewSummaryDto.toCourseReviewSummary(): CourseReviewSummary = CourseReviewSummary(
    id = id,
    course = course,
    average = average.trimToTenths(),
    count = count
)

@SuppressLint("DefaultLocale")
private fun Double.trimToTenths(): Double {
    return String.format("%.1f", this).toDouble()
}