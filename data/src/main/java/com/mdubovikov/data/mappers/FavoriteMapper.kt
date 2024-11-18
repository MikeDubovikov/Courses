package com.mdubovikov.data.mappers

import com.mdubovikov.data.database.entity.FavoriteCourse
import com.mdubovikov.domain.catalog.entity.CourseCard

fun CourseCard.toFavoriteCourse(): FavoriteCourse = FavoriteCourse(
    id = id,
    title = title,
    cover = cover,
    summary = summary,
    rating = rating,
    displayPrice = displayPrice,
    createDate = createDate
)

fun FavoriteCourse.toCourseCard(): CourseCard =
    CourseCard(
        id = id,
        title = title,
        cover = cover,
        summary = summary,
        rating = rating,
        displayPrice = displayPrice,
        createDate = createDate
    )

fun List<FavoriteCourse>.toFavoritesCourses(): List<CourseCard> = map { it.toCourseCard() }