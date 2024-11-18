package com.mdubovikov.presentation.details_screen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mdubovikov.common.Container
import com.mdubovikov.domain.catalog.entity.CourseDetails
import com.mdubovikov.presentation.R
import com.mdubovikov.presentation.databinding.FragmentDetailsBinding
import com.mdubovikov.presentation.mappers.toCourseCard
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw IllegalStateException("Fragment $this binding cannot be accessed")

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setupCourseId(args.courseId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.courseDetails.collectLatest {
                    with(binding) {
                        when (it) {
                            is Container.Pending -> {
                                progressBar.visibility = VISIBLE
                                detailsScreen.visibility = GONE
                                courseError.visibility = GONE
                            }

                            is Container.Success -> {
                                progressBar.visibility = GONE
                                detailsScreen.visibility = VISIBLE
                                courseError.visibility = GONE
                                setupUi(it.value)

                            }

                            is Container.Error -> {
                                Log.d("error", it.exception.toString())
                                progressBar.visibility = GONE
                                detailsScreen.visibility = GONE
                                courseError.visibility = VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupUi(courseDetails: CourseDetails) = binding.apply {

        courseTitle.text = courseDetails.title
        courseRating.text = courseDetails.rating.toString()
        courseDate.text = courseDetails.createDate
        courseAuthorName.text = courseDetails.authorName
        courseDescription.text = courseDetails.description

        buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        buttonChangeFavoriteStatus.setOnClickListener {
            lifecycleScope.launch {
                viewModel.changeFavoriteStatus(courseDetails.toCourseCard())
                val isFavorite = viewModel.isFavorite.value
                val response = if (isFavorite == true) {
                    getString(R.string.course_added)
                } else {
                    getString(R.string.course_removed)
                }
                Toast.makeText(requireContext(), response, Toast.LENGTH_SHORT).show()
            }
        }

        goToLink(buttonStartCourse, courseDetails, true)
        goToLink(buttonToPlatform, courseDetails, false)

        Glide.with(courseImage)
            .load(courseDetails.cover)
            .into(courseImage)

        Glide.with(courseAuthorImage)
            .load(courseDetails.authorAvatar)
            .into(courseAuthorImage)

    }

    private fun goToLink(button: View, course: CourseDetails, isTrim: Boolean) {
        button.setOnClickListener {
            val courseLink = course.courseUrl
            if (courseLink.isNotBlank()) {
                if (isTrim) {
                    val coursePlatform = courseLink.split("/course").first()
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(coursePlatform))
                    startActivity(intent)
                } else {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(courseLink))
                    startActivity(intent)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.something_went_wrong),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}