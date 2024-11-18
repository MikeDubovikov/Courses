package com.mdubovikov.presentation.favorites_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.mdubovikov.presentation.adapter.CourseItem
import com.mdubovikov.presentation.adapter.MainScreenAdapter
import com.mdubovikov.presentation.databinding.FragmentFavoritesBinding
import com.mdubovikov.presentation.mappers.toCourseItem
import com.mdubovikov.presentation.mappers.toCourseItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: FavoriteViewModel by viewModels()

    private val adapter by lazy {
        MainScreenAdapter(
            glide = Glide.with(this),
            onItemClick = ::onCourseClick,
            onChangeStatusClick = ::onChangeStatusClick
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCourses.adapter = adapter
        observeFavoriteMeals()
    }

    private fun observeFavoriteMeals() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favoriteCourses.collect {
                    if (it.isNotEmpty()) {
                        binding.favoritesEmpty.visibility = View.GONE
                    } else {
                        binding.favoritesEmpty.visibility = View.VISIBLE
                    }
                    adapter.apply {
                        items = it.toCourseItems()
                    }
                }
            }
        }
    }

    private fun onCourseClick(courseId: Long) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(courseId)
        findNavController().navigate(action)
    }

    private fun onChangeStatusClick(courseCard: CourseItem) {
        lifecycleScope.launch {
            viewModel.changeFavoriteStatus(courseCard.toCourseItem())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}