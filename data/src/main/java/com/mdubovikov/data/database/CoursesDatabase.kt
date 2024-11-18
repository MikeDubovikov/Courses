package com.mdubovikov.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mdubovikov.data.database.dao.FavoritesDao
import com.mdubovikov.data.database.entity.FavoriteCourse

@Database(entities = [FavoriteCourse::class], version = 1, exportSchema = false)
abstract class CoursesDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object {

        private const val DB_NAME = "CoursesDatabase"
        private var INSTANCE: CoursesDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context): CoursesDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context = context,
                    klass = CoursesDatabase::class.java,
                    name = DB_NAME
                ).build()

                INSTANCE = database
                return database
            }
        }
    }
}