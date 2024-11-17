package com.mdubovikov.domain.catalog.usecases

import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.domain.catalog.repository.CatalogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCourseUseCase @Inject constructor(
    private val catalogRepository: CatalogRepository
) {
    fun getCourse(courseId: Long): Flow<Container<CourseDetails>> = flow {
        try {
            emit(Container.Pending)
            val courseCard = catalogRepository.getCourse(courseId)
            val reviewSummary = catalogRepository.getCourseReviewSummary(courseCard.id)
            val authorInfo = catalogRepository.getCourseAuthor(courseCard.authorId)
            val courseCardFinal = courseCard.copy(
                rating = reviewSummary.average,
                authorName = authorInfo.fullName,
                authorAvatar = authorInfo.avatar
            )
            emit(Container.Success(value = courseCardFinal))
        } catch (e: Exception) {
            emit(Container.Error(exception = e))
        }
    }
}