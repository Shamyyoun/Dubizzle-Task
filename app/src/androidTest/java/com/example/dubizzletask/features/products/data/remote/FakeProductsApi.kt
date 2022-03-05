package com.example.dubizzletask.features.products.data.remote

import com.example.dubizzletask.features.products.data.FakeProductsData
import com.example.dubizzletask.features.products.data.remote.dto.ProductsApi
import com.example.dubizzletask.features.products.data.remote.dto.ProductsResponse
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class FakeProductsApi @Inject constructor(private val gson: Gson) : ProductsApi {

    override suspend fun getProducts(): Response<ProductsResponse> =
        with(FakeProductsData.productsResponse) {
            return if (first == 200) {
                val productsResponse = gson.fromJson(second, ProductsResponse::class.java)
                return Response.success(productsResponse)
            } else {
                Response.error(first, second.toResponseBody("application/json".toMediaType()))
            }
        }
}