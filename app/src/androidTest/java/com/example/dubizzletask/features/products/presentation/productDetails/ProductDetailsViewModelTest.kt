package com.example.dubizzletask.features.products.presentation.productDetails

import com.example.dubizzletask.core.BaseAndroidTest
import com.example.dubizzletask.di.AppModule
import com.example.dubizzletask.features.products.domain.models.Product
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProductDetailsViewModelTest : BaseAndroidTest() {
    private val viewModel = ProductDetailsViewModel()

    @Test
    fun view_model_is_changing_the_view_state_product_state_when_product_is_set() {
        runBlockingTest {
            // Given
            val product = Product(
                uid = "199",
                name = "Glasses"
            )

            // When
            viewModel.setProduct(product)

            // Then
            assertTrue(viewModel.viewState.value is ProductDetailsViewState.ProductState)
            val emittedProduct = (viewModel.viewState.value as ProductDetailsViewState.ProductState).product
            assertEquals("199", emittedProduct.uid)
            assertEquals("Glasses", emittedProduct.name)
        }
    }
}