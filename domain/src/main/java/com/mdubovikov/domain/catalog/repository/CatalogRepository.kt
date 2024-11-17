package com.mdubovikov.domain.catalog.repository

import com.mdubovikov.domain.catalog.entity.CourseAuthor
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.domain.catalog.entity.CourseReviewSummary

interface CatalogRepository {
    suspend fun getCourses(): List<CourseCard>
    suspend fun getCourse(courseId: Long): CourseDetails
    suspend fun getCourseReviewSummary(courseId: Long): CourseReviewSummary
    suspend fun getCourseAuthor(authorId: Long): CourseAuthor
}