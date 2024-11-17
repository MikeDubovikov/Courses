package com.mdubovikov.data.mappers

import com.mdubovikov.data.network.dto.AuthorDto
import com.mdubovikov.domain.catalog.entity.CourseAuthor

fun AuthorDto.toCourseAuthor(): CourseAuthor = CourseAuthor(
    id = id,
    fullName = fullName,
    avatar = avatar
)