package com.example.dubizzletask.features.products.data.repository

import com.example.dubizzletask.core.BaseRepository
import com.example.dubizzletask.features.products.data.remote.dto.ProductsApi
import com.example.dubizzletask.features.products.data.remote.dto.toProduct
import com.example.dubizzletask.features.products.domain.models.Product
import com.example.dubizzletask.features.products.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val productsApi: ProductsApi) :
    ProductsRepository, BaseRepository() {

    override suspend fun getProducts(): List<Product> {
        val response = handleApiResponse(
            productsApi.getProducts()
        )

        return response.results.map {
            it.toProduct()
        }
    }
}