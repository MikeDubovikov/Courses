package com.mdubovikov.presentation.adapter

data class CourseItem(
    val id: Long,
    val title: String,
    val cover: String,
    val summary: String,
    val rating: Double,
    val displayPrice: String,
    val createDate: String
) : ListItem {
    override val itemId: Long = id
}

