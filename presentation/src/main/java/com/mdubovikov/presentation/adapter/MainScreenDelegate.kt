package com.mdubovikov.presentation.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mdubovikov.presentation.R
import com.mdubovikov.presentation.databinding.CourseCardBinding

object MainScreenDelegate {

    @SuppressLint("SetTextI18n")
    fun courseDelegate(
        glide: RequestManager,
        onItemClick: (courseId: Long) -> Unit,
        onChangeStatusClick: (course: CourseItem) -> Unit
    ) = adapterDelegateViewBinding<CourseItem, ListItem, CourseCardBinding>(
        { inflater, container -> CourseCardBinding.inflate(inflater, container, false) }
    ) {
        bind {
            with(binding) {
                courseTitle.text = item.title
                courseDate.text = item.createDate
                courseSummary.text = item.summary
                coursePrice.text = item.displayPrice
                courseRating.text = item.rating.toString()

                if (item.isFavorite) {
                    imageFavoriteStatus.setImageResource(R.drawable.ic_favorite_button_filled)
                } else {
                    imageFavoriteStatus.setImageResource(R.drawable.ic_favorite_button)
                }

                if (item.displayPrice.contains("-")) {
                    coursePrice.text = "Бесплатно"
                } else {
                    item.displayPrice
                }

                glide.load(item.cover).into(courseImage)

                courseFavoriteStatusCard.setOnClickListener {
                    onChangeStatusClick.invoke(item)
                }

                root.setOnClickListener {
                    onItemClick.invoke(item.id)
                }
            }
        }
    }
}