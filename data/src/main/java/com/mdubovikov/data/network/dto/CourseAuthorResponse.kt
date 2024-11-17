package com.mdubovikov.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseAuthorResponse(
    val users: List<AuthorDto>
)

@Serializable
data class AuthorDto(
    val id: Long,
    @SerialName("full_name")
    val fullName: String,
    val avatar: String
)