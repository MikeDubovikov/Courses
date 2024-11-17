package com.mdubovikov.data.network.repositories

import com.mdubovikov.data.mappers.toCourseAuthor
import com.mdubovikov.data.mappers.toCourseDetails
import com.mdubovikov.data.mappers.toCourseReviewSummary
import com.mdubovikov.data.mappers.toCoursesCard
import com.mdubovikov.data.network.api.ApiService
import com.mdubovikov.domain.catalog.entity.CourseAuthor
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.domain.catalog.entity.CourseReviewSummary
import com.mdubovikov.domain.catalog.repository.CatalogRepository
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CatalogRepository {

    override suspend fun getCourses(): List<CourseCard> {
        return apiService.getAllCourses(5).body()?.courses?.toCoursesCard() ?: emptyList()
    }

    override suspend fun getCourse(courseId: Long): CourseDetails {
        return apiService.getCourse(courseId).body()?.courses?.first()?.toCourseDetails()
            ?: CourseDetails()
    }

    override suspend fun getCourseReviewSummary(courseId: Long): CourseReviewSummary {
        return apiService.getCourseReviewSummary(courseId).body()?.courseReviewSummaries?.first()
            ?.toCourseReviewSummary()
            ?: CourseReviewSummary()
    }

    override suspend fun getCourseAuthor(authorId: Long): CourseAuthor {
        return apiService.getCourseAuthor(authorId).body()?.users?.first()?.toCourseAuthor()
            ?: CourseAuthor()
    }
}