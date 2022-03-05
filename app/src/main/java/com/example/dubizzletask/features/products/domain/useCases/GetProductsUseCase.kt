package com.example.dubizzletask.features.products.domain.useCases

import com.example.dubizzletask.common.Resource
import com.example.dubizzletask.core.BaseUseCase
import com.example.dubizzletask.features.products.domain.models.Product
import com.example.dubizzletask.features.products.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) :
    BaseUseCase() {

    operator fun invoke(): Flow<Resource<List<Product>>> = flow {
        tryFlow {
            // Loading
            emit(
                Resource.Loading()
            )

            // Get products
            val products = productsRepository.getProducts()
            emit(
                Resource.Success(
                    data = products
                )
            )
        }
    }
}