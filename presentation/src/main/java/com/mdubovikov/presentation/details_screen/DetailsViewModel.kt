package com.mdubovikov.presentation.details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.domain.catalog.usecases.ChangeFavoriteStatusUseCase
import com.mdubovikov.domain.catalog.usecases.CourseFavoriteStatusUseCase
import com.mdubovikov.domain.catalog.usecases.GetCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    getCourseUseCase: GetCourseUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
    private val courseFavoriteStatusUseCase: CourseFavoriteStatusUseCase
) : ViewModel() {

    private val courseId: MutableStateFlow<Long?> = MutableStateFlow(null)
    private val _isFavorite: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isFavorite: StateFlow<Boolean?> = _isFavorite.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val courseDetails: StateFlow<Container<CourseDetails>> = courseId
        .flatMapLatest { courseId ->
            if (courseId != null) {
                getCourseUseCase.getCourse(courseId = courseId)
            } else flowOf()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Container.Pending
        )

    suspend fun changeFavoriteStatus(course: CourseCard) {
        val isFavorite = courseFavoriteStatusUseCase.invoke(course.id).first()
        if (isFavorite) {
            changeFavoriteStatusUseCase.removeCourseFromFavorites(course.id)
            _isFavorite.value = false
        } else {
            changeFavoriteStatusUseCase.addCourseToFavorite(course)
            _isFavorite.value = true
        }
    }

    fun setupCourseId(courseId: Long) {
        this.courseId.value = courseId
    }
}