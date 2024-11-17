package com.mdubovikov.domain.catalog.usecases

import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.repository.CatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCoursesUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    fun getCourses(): Flow<Container<List<CourseCard>>> = flow {
        try {
            emit(Container.Pending)
            val courses = catalogRepository.getCourses().map {
                val reviewSummary = catalogRepository.getCourseReviewSummary(it.id)
                it.copy(rating = reviewSummary.average)
            }
            emit(Container.Success(value = courses))
        } catch (e: Exception) {
            emit(Container.Error(exception = e))
        }
    }
}