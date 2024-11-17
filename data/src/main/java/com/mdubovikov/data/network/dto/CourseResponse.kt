package com.mdubovikov.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    val meta: Meta,
    val courses: List<CourseDto>
)

@Serializable
data class Meta(
    val page: Int,
    @SerialName("has_next")
    val hasNext: Boolean,
    @SerialName("has_previous")
    val hasPrevious: Boolean
)

@Serializable
data class CourseDto(
    val id: Long,
    val title: String,
    val cover: String,
    val summary: String,
    @SerialName("authors")
    val authorId: List<Long>,
    val description: String,
    @SerialName("display_price")
    val displayPrice: String,
    @SerialName("create_date")
    val createDate: String,
    @SerialName("canonical_url")
    val courseUrl: String
)