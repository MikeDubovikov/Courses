package com.mdubovikov.domain.catalog.entity

data class CourseCard(
    val id: Long = -1,
    val title: String = "",
    val cover: String = "",
    val summary: String = "",
    val rating: Double = 0.0,
    val displayPrice: String = "",
    val createDate: String = "",
    val isFavorite: Boolean = false
)