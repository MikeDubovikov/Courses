package com.mdubovikov.domain.catalog.repository

import com.mdubovikov.domain.catalog.entity.CourseAuthor
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.domain.catalog.entity.CourseReviewSummary
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    suspend fun getCourses(): List<CourseCard>
    suspend fun getCourse(courseId: Long): CourseDetails
    suspend fun getCourseReviewSummary(courseId: Long): CourseReviewSummary
    suspend fun getCourseAuthor(authorId: Long): CourseAuthor
    fun getCourseIdsFromFavorites(): Flow<List<Int>>
    suspend fun addToFavorites(course: CourseCard)
    suspend fun removeFromFavorites(courseId: Long)
}