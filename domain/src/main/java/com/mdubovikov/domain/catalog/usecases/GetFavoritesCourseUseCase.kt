package com.mdubovikov.domain.catalog.usecases

import com.mdubovikov.domain.catalog.repository.CatalogRepository
import javax.inject.Inject

class GetFavoritesCourseUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    operator fun invoke() = catalogRepository.getFavoritesCourse()
}