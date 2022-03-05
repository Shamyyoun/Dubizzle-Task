package com.example.dubizzletask.features.products.presentation.productsList

import com.example.dubizzletask.features.products.domain.models.Product

sealed class ProductsListEvent {
    object FetchProducts : ProductsListEvent()
    data class ProductClicked(val product: Product) : ProductsListEvent()
}