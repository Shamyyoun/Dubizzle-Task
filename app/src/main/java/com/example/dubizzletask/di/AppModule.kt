package com.example.dubizzletask.di

import com.example.dubizzletask.features.products.data.remote.dto.ProductsApi
import com.example.dubizzletask.features.products.data.repository.ProductsRepositoryImpl
import com.example.dubizzletask.features.products.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @EndPointUrl
    fun provideEndPointUrl(): String {
        return "https://ey3f2y0nre.execute-api.us-east-1.amazonaws.com"
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, @EndPointUrl endPointUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(endPointUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideProductsApi(retrofit: Retrofit): ProductsApi {
        return retrofit.create(ProductsApi::class.java)
    }

    @Provides
    fun provideProductsRepository(productsApi: ProductsApi): ProductsRepository {
        return ProductsRepositoryImpl(productsApi)
    }
}