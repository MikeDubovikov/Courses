package com.mdubovikov.presentation.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseCard
import com.mdubovikov.presentation.R
import com.mdubovikov.presentation.adapter.CourseItem
import com.mdubovikov.presentation.adapter.MainScreenAdapter
import com.mdubovikov.presentation.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy {
        MainScreenAdapter(
            glide = Glide.with(this),
            onItemClick = ::onCourseClick,
            onChangeStatusClick = ::onCourseClick
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCourses.adapter = adapter
        loadData()
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.courses.collectLatest { courses ->
                    with(binding) {
                        when (courses) {
                            Container.Pending -> {
                                progressBar.visibility = VISIBLE
                                rvCourses.visibility = GONE
                                courseError.visibility = GONE
                            }

                            is Container.Success -> {
                                rvCourses.visibility = VISIBLE
                                progressBar.visibility = GONE
                                courseError.visibility = GONE
                                map(courses.value)
                            }

                            is Container.Error -> {
                                progressBar.visibility = GONE
                                rvCourses.visibility = GONE
                                courseError.visibility = VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun map(courseCardList: List<CourseCard>) {

        val newCourses = courseCardList.map { course ->

            val correctPrice = if (course.displayPrice.contains("-")) {
                getString(R.string.free)
            } else {
                course.displayPrice
            }

            CourseItem(
                id = course.id,
                title = course.title,
                cover = course.cover,
                summary = course.summary,
                rating = course.rating,
                displayPrice = correctPrice,
                createDate = course.createDate
            )
        }

        adapter.apply {
            items = newCourses
        }
    }

    private fun onCourseClick(courseId: Long) {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(courseId)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}