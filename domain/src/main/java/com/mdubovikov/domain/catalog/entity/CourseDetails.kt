package com.mdubovikov.domain.catalog.entity

data class CourseDetails(
    val id: Long = -1,
    val title: String = "",
    val cover: String = "",
    val summary: String = "",
    val authorId: Long = -1,
    val authorName: String = "",
    val authorAvatar: String = "",
    val description: String = "",
    val rating: Double = 0.0,
    val displayPrice: String = "",
    val createDate: String = "",
    val courseUrl: String = "",
    val isFavorite: Boolean = false
)