package com.mdubovikov.presentation.adapter

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MainScreenAdapter(
    glide: RequestManager,
    onItemClick: (courseId: Long) -> Unit,
    onChangeStatusClick: (courseId: Long) -> Unit
) : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager.addDelegate(
            MainScreenDelegate.courseDelegate(
                glide = glide,
                onItemClick = onItemClick,
                onChangeStatusClick = onChangeStatusClick
            )
        )
    }
}
