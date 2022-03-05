package com.example.dubizzletask.features.products.data.remote.dto

import com.example.dubizzletask.features.products.domain.models.Product
import com.google.gson.annotations.SerializedName

data class ProductsResponse(
    @SerializedName("results")
    val results: List<ProductDto> = listOf()
)

data class ProductDto(
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("image_ids")
    val imageIds: List<String> = listOf(),
    @SerializedName("image_urls")
    val imageUrls: List<String> = listOf(),
    @SerializedName("image_urls_thumbnails")
    val imageUrlsThumbnails: List<String> = listOf(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("uid")
    val uid: String = ""
)

fun ProductDto.toProduct() = Product(
    createdAt = createdAt,
    imageIds = imageIds,
    imageUrls = imageUrls,
    imageUrlsThumbnails = imageUrlsThumbnails,
    name = name,
    price = price,
    uid = uid
)