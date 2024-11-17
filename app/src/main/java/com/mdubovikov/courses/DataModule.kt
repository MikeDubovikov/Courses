package com.mdubovikov.courses

import com.mdubovikov.data.network.api.ApiFactory
import com.mdubovikov.data.network.api.ApiService
import com.mdubovikov.data.network.repositories.CatalogRepositoryImpl
import com.mdubovikov.domain.catalog.repository.CatalogRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface DataModule {

    @Binds
    fun bindCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository

    companion object {

        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService
    }
}