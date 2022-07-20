package com.example.dubizzletask.di

import com.example.dubizzletask.features.products.data.remote.dto.ProductsApi
import com.example.dubizzletask.features.products.data.repository.ProductsRepositoryImpl
import com.example.dubizzletask.features.products.domain.repository.ProductsRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @EndPointPort
    fun provideEndPointPort(): Int {
        return 8080
    }

    @Provides
    @EndPointUrl
    fun provideEndPointUrl(@EndPointPort port: Int): String {
        return "http://127.0.0.1:$port"
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(1, TimeUnit.SECONDS)
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
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

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideMockWebServer(): MockWebServer {
        return MockWebServer()
    }
}