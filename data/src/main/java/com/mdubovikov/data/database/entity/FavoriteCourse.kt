package com.mdubovikov.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCourse(
    @PrimaryKey val id: Long,
    val title: String,
    val cover: String,
    val summary: String,
    val rating: Double,
    val displayPrice: String,
    val createDate: String,
    val isFavorite:Boolean
)
