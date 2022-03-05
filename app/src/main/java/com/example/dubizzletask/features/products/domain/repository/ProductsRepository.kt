package com.example.dubizzletask.features.products.domain.repository

import com.example.dubizzletask.features.products.domain.models.Product

interface ProductsRepository {
    suspend fun getProducts(): List<Product>
}