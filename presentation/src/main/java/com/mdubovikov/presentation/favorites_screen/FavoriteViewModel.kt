package com.mdubovikov.presentation.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.usecases.ChangeFavoriteStatusUseCase
import com.mdubovikov.domain.catalog.usecases.CourseFavoriteStatusUseCase
import com.mdubovikov.domain.catalog.usecases.GetFavoritesCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoritesCourseUseCase: GetFavoritesCourseUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
    private val courseFavoriteStatusUseCase: CourseFavoriteStatusUseCase
) : ViewModel() {

    private val _favoriteCourses: MutableStateFlow<List<CourseCard>> = MutableStateFlow(listOf())
    val favoriteCourses: StateFlow<List<CourseCard>> = _favoriteCourses.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoritesCourseUseCase.invoke().collect {
                _favoriteCourses.value = it
            }
        }
    }

    suspend fun changeFavoriteStatus(course: CourseCard) {
        val isFavorite = courseFavoriteStatusUseCase.invoke(course.id).first()
        if (isFavorite) {
            changeFavoriteStatusUseCase.removeCourseFromFavorites(course.id)
        } else {
            changeFavoriteStatusUseCase.addCourseToFavorite(course)
        }
    }
}