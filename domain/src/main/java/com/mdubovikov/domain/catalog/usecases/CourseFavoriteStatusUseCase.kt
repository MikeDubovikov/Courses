package com.mdubovikov.domain.catalog.usecases

import com.mdubovikov.domain.catalog.repository.CatalogRepository
import javax.inject.Inject

class CourseFavoriteStatusUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    operator fun invoke(courseId: Long) = catalogRepository.courseIsFavorite(courseId)
}