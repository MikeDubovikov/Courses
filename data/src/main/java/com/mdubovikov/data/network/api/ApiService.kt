package com.mdubovikov.data.network.api

import com.mdubovikov.data.network.dto.CourseAuthorResponse
import com.mdubovikov.data.network.dto.CourseResponse
import com.mdubovikov.data.network.dto.CourseReviewSummaryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("courses")
    suspend fun getAllCourses(
        @Query("page") page: Int
    ): Response<CourseResponse>

    @GET("courses")
    suspend fun getCourse(
        @Query("ids[]") courseId: Long
    ): Response<CourseResponse>

    @GET("course-review-summaries")
    suspend fun getCourseReviewSummary(
        @Query("course") courseId: Long
    ): Response<CourseReviewSummaryResponse>

    @GET("users")
    suspend fun getCourseAuthor(
        @Query("ids[]") authorId: Long
    ): Response<CourseAuthorResponse>
}