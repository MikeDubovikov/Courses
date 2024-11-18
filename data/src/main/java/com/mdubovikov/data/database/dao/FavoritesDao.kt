package com.mdubovikov.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mdubovikov.data.database.entity.FavoriteCourse
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteCourse>>

    @Query("SELECT EXISTS (SELECT * FROM favorites WHERE id=:itemId LIMIT 1)")
    fun courseIsFavorite(itemId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(itemDbModel: FavoriteCourse)

    @Query("DELETE FROM favorites WHERE id=:itemId")
    suspend fun removeFromFavorites(itemId: Long)
}