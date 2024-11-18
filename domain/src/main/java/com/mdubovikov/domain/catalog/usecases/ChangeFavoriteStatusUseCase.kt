package com.mdubovikov.domain.catalog.usecases

import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.repository.CatalogRepository
import javax.inject.Inject

class ChangeFavoriteStatusUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun addCourseToFavorite(course: CourseCard) = catalogRepository.addToFavorites(course)
    suspend fun removeCourseFromFavorites(courseId: Long) = catalogRepository.removeFromFavorites(courseId)
}