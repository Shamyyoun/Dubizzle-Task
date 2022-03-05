package com.example.dubizzletask.features.products.data.remote.dto

import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("/default/dynamodb-writer")
    suspend fun getProducts(): Response<ProductsResponse>
}