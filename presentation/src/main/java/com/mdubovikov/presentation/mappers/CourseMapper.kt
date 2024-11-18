package com.mdubovikov.presentation.mappers


import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.presentation.adapter.CourseItem

fun CourseDetails.toCourseCard(): CourseCard = CourseCard(
    id = id,
    title = title,
    cover = cover,
    summary = summary,
    rating = rating,
    displayPrice = displayPrice,
    createDate = createDate,
    isFavorite = isFavorite
)

fun CourseCard.toCourseItem(): CourseItem = CourseItem(
    id = id,
    title = title,
    cover = cover,
    summary = summary,
    rating = rating,
    displayPrice = displayPrice,
    createDate = createDate,
    isFavorite = isFavorite
)

fun CourseItem.toCourseItem(): CourseCard = CourseCard(
    id = id,
    title = title,
    cover = cover,
    summary = summary,
    rating = rating,
    displayPrice = displayPrice,
    createDate = createDate,
    isFavorite = isFavorite
)

fun List<CourseCard>.toCourseItems(): List<CourseItem> = map { it.toCourseItem() }