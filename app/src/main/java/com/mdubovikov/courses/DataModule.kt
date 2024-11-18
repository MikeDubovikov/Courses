package com.mdubovikov.courses

import android.content.Context
import com.mdubovikov.data.database.CoursesDatabase
import com.mdubovikov.data.database.dao.FavoritesDao
import com.mdubovikov.data.network.api.ApiFactory
import com.mdubovikov.data.network.api.ApiService
import com.mdubovikov.data.network.repositories.CatalogRepositoryImpl
import com.mdubovikov.domain.catalog.repository.CatalogRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface DataModule {

    @Binds
    fun bindCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository

    companion object {

        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService

        @Provides
        fun provideCoursesDatabase(@ApplicationContext context: Context): CoursesDatabase {
            return CoursesDatabase.getInstance(context)
        }

        @Provides
        fun provideFavoritesDao(database: CoursesDatabase): FavoritesDao {
            return database.favoritesDao()
        }
    }
}