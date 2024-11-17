package com.mdubovikov.presentation.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.domain.catalog.usecases.GetAllCoursesUseCase
import com.mdubovikov.domain.catalog.usecases.GetCourseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getAllCoursesUseCase: GetAllCoursesUseCase,
    getCourseUseCase: GetCourseUseCase
) : ViewModel() {

    val courses: StateFlow<Container<List<CourseCard>>> = getAllCoursesUseCase.getCourses()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = Container.Pending
        )

    val course: StateFlow<Container<CourseDetails>> =
        getCourseUseCase.getCourse(58852)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = Container.Pending
            )
}