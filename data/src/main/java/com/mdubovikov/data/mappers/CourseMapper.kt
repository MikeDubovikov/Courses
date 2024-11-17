package com.mdubovikov.data.mappers

import com.mdubovikov.data.network.dto.CourseDto
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.entity.CourseDetails
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun CourseDto.toCourseCard(): CourseCard = CourseCard(
    id = id,
    title = title,
    cover = cover,
    summary = summary,
    displayPrice = displayPrice,
    createDate = createDate.formatDate()
)

fun List<CourseDto>.toCoursesCard(): List<CourseCard> = map { it.toCourseCard() }

fun CourseDto.toCourseDetails(): CourseDetails = CourseDetails(
    id = id,
    title = title,
    cover = cover,
    authorId = authorId.first(),
    description = description,
    displayPrice = displayPrice,
    createDate = createDate.formatDate(),
    courseUrl = courseUrl
)

private fun String.formatDate(): String {
    val zonedDateTime = ZonedDateTime.parse(this)
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())
    return zonedDateTime.format(formatter)
}