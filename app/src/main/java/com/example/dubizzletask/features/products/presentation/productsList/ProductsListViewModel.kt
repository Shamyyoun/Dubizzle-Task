package com.example.dubizzletask.features.products.presentation.productsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dubizzletask.common.AppError
import com.example.dubizzletask.common.Resource
import com.example.dubizzletask.core.BaseViewModel
import com.example.dubizzletask.features.products.domain.models.Product
import com.example.dubizzletask.features.products.domain.useCases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(private val getProducts: GetProductsUseCase) :
    BaseViewModel() {

    private val _viewState = MutableLiveData<ProductsListViewState>()
    val viewState: LiveData<ProductsListViewState>
        get() = _viewState

    init {
        fetchProducts()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    fun onEvent(event: ProductsListEvent) = when (event) {
        is ProductsListEvent.FetchProducts -> fetchProducts()

        is ProductsListEvent.ProductClicked -> navigateToProductDetails(event.product)
    }

    private fun fetchProducts() = getProducts().onEach {
        when (it) {
            is Resource.Loading -> _viewState.value = ProductsListViewState.Loading

            is Resource.Success -> _viewState.value = if (it.data.isNotEmpty()) {
                ProductsListViewState.Products(it.data)
            } else {
                ProductsListViewState.Empty
            }

            is Resource.Error -> _viewState.value = ProductsListViewState.Error(
                message = if (it.error is AppError.ApiErrorMessage) {
                    it.error.message
                } else {
                    null
                }
            )
        }
    }.launchIn(viewModelScope)

    private fun navigateToProductDetails(product: Product) {
        navigate(
            ProductsListFragmentDirections.actionNavProductsListToNavProductDetails(product)
        )
    }
}