package com.example.dubizzletask.features.products.presentation.productsList

import com.example.dubizzletask.common.NavigationCommand
import com.example.dubizzletask.core.BaseAndroidTest
import com.example.dubizzletask.core.enqueueResponse
import com.example.dubizzletask.di.AppModule
import com.example.dubizzletask.features.products.domain.models.Product
import com.example.dubizzletask.features.products.domain.useCases.GetProductsUseCase
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProductsListViewModelTest : BaseAndroidTest() {
    @Inject
    lateinit var getProductsUseCase: GetProductsUseCase

    @Test
    fun view_model_is_changing_the_view_state_to_error_when_products_api_failed() = runBlocking {
        // Given
        mockWebServer.enqueueResponse(400, "products-400.json")

        // When
        val viewModel = ProductsListViewModel(getProductsUseCase)

        // Then
        assertTrue(viewModel.viewState.value is ProductsListViewState.Loading)

        delay(500)

        val errorState = viewModel.viewState.value
        assertTrue(errorState is ProductsListViewState.Error)
        assertEquals("api error", (errorState as ProductsListViewState.Error).message)
    }

    @Test
    fun view_model_is_changing_the_view_state_to_products_when_getProductsUseCase_emits_products_list() = runBlocking {
        // Given
        mockWebServer.enqueueResponse(200, "products-200.json")

        // When
        val viewModel = ProductsListViewModel(getProductsUseCase)

        // Then
        assertTrue(viewModel.viewState.value is ProductsListViewState.Loading)

        delay(500)

        assertTrue(viewModel.viewState.value is ProductsListViewState.Products)
        val successState = viewModel.viewState.value as ProductsListViewState.Products
        assertEquals("Notebook", successState.products[0].name)
        assertEquals("Glasses", successState.products[1].name)
    }

    @Test
    fun view_model_is_changing_the_view_state_to_empty_when_getProductsUseCase_emits_empty_list()  = runBlocking {
        // Given
        mockWebServer.enqueueResponse(200, "products-200-empty.json")

        // When
        val viewModel = ProductsListViewModel(getProductsUseCase)

        // Then
        assertTrue(viewModel.viewState.value is ProductsListViewState.Loading)
        delay(500)
        assertTrue(viewModel.viewState.value is ProductsListViewState.Empty)
    }

    @Test
    fun view_model_is_navigating_to_product_details_when_ProductClicked_event_happened() {
        // Given
        val event = ProductsListEvent.ProductClicked(
            product = Product(name = "Notebook")
        )

        // When
        val viewModel = ProductsListViewModel(getProductsUseCase)
        viewModel.onEvent(event)

        // Then
        val navCommand = viewModel.navigation.value
        assertTrue(navCommand is NavigationCommand.ToDirection)

        val directions = (navCommand as NavigationCommand.ToDirection).directions
        assertTrue(directions is ProductsListFragmentDirections.ActionNavProductsListToNavProductDetails)
    }
}