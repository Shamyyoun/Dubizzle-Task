package com.example.dubizzletask.di

import com.example.dubizzletask.features.products.data.remote.FakeProductsApi
import com.example.dubizzletask.features.products.data.remote.dto.ProductsApi
import com.example.dubizzletask.features.products.data.repository.ProductsRepositoryImpl
import com.example.dubizzletask.features.products.domain.repository.ProductsRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    fun provideProductsApi(gson: Gson): ProductsApi {
        return FakeProductsApi(gson)
    }

    @Provides
    fun provideProductsRepository(productsApi: ProductsApi): ProductsRepository {
        return ProductsRepositoryImpl(productsApi)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }
}