package com.example.dubizzletask.features.products.presentation.productsList

import com.example.dubizzletask.features.products.domain.models.Product

sealed class ProductsListViewState {
    object Loading : ProductsListViewState()
    data class Error(val message: String? = null) : ProductsListViewState()
    data class Products(val products: List<Product>) : ProductsListViewState()
    object Empty : ProductsListViewState()
}