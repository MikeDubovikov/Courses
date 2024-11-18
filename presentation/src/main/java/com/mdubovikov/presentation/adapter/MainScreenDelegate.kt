package com.mdubovikov.presentation.adapter

import android.annotation.SuppressLint
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.mdubovikov.presentation.databinding.CourseCardBinding

object MainScreenDelegate {

    @SuppressLint("SetTextI18n")
    fun courseDelegate(
        glide: RequestManager,
        onItemClick: (courseId: Long) -> Unit,
        onChangeStatusClick: (courseId: Long) -> Unit
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

                glide.load(item.cover).into(courseImage)

                courseFavoriteStatusCard.setOnClickListener {
                    onChangeStatusClick.invoke(item.id)
                }

                root.setOnClickListener {
                    onItemClick.invoke(item.id)
                }
            }
        }
    }
}