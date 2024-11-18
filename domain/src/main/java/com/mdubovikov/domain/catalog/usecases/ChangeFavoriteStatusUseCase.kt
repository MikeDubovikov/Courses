package com.mdubovikov.domain.catalog.usecases

import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.repository.CatalogRepository
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

class ChangeFavoriteStatusUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    suspend fun changeStatus(course: CourseCard) = withTimeout(3000) {
        val courseIdsInFavorites = catalogRepository.getCourseIdsFromFavorites()
            .filterIsInstance<Container.Success<Set<Long>>>()
            .first()
        if (!courseIdsInFavorites.value.contains(course.id)) {
            catalogRepository.addToFavorites(course)
        } else {
            catalogRepository.removeFromFavorites(course.id)
        }
    }
}