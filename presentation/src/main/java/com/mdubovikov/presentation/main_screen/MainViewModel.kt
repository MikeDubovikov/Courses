package com.mdubovikov.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.usecases.ChangeFavoriteStatusUseCase
import com.mdubovikov.domain.catalog.usecases.CourseFavoriteStatusUseCase
import com.mdubovikov.domain.catalog.usecases.GetAllCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAllCoursesUseCase: GetAllCoursesUseCase,
    private val courseFavoriteStatusUseCase: CourseFavoriteStatusUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase
) : ViewModel() {

    val courses: StateFlow<Container<List<CourseCard>>> = getAllCoursesUseCase.getCourses()
        .map(::processResult)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Container.Pending
        )

    suspend fun changeFavoriteStatus(course: CourseCard) {
        val isFavorite = courseFavoriteStatusUseCase.invoke(course.id).first()
        if (isFavorite) {
            changeFavoriteStatusUseCase.removeCourseFromFavorites(course.id)
        } else {
            changeFavoriteStatusUseCase.addCourseToFavorite(course)
        }
    }

    private suspend fun processResult(result: Container<List<CourseCard>>): Container<List<CourseCard>> =
        when (result) {
            is Container.Pending -> Container.Pending
            is Container.Success -> {
                val value = result.value
                    .map { item ->
                        if (courseFavoriteStatusUseCase.invoke(item.id).first())
                            item.copy(isFavorite = item.isFavorite.not())
                        else
                            item
                    }
                Container.Success(value)
            }

            is Container.Error -> Container.Error(result.exception)
        }

}