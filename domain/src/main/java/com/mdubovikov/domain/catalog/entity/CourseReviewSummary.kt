package com.mdubovikov.domain.catalog.entity

data class CourseReviewSummary(
    val id: Int = -1,
    val course: Int = -1,
    val average: Double = -1.0,
    val count: Int = -1
)
