package com.example.dubizzletask.features.products.domain.useCases

import app.cash.turbine.test
import com.example.dubizzletask.common.AppError
import com.example.dubizzletask.common.Resource
import com.example.dubizzletask.common.exceptions.ErrorResponseMessageException
import com.example.dubizzletask.features.products.domain.models.Product
import com.example.dubizzletask.features.products.domain.repository.ProductsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.given
import org.powermock.api.mockito.PowerMockito
import java.io.IOException

@ExperimentalCoroutinesApi
class GetProductsUseCaseTest {
    private val productsRepository = PowerMockito.mock(ProductsRepository::class.java)
    private val getProductsUseCase = GetProductsUseCase(productsRepository)

    @Test
    fun `use case is emitting loading`() = runBlockingTest {
        // Given

        // When
        val flow = getProductsUseCase()

        // Then
        flow.test {
            assertTrue(
                awaitItem() is Resource.Loading
            )

            awaitItem()
            awaitComplete()
        }
    }

    @Test
    fun `use case is emitting success with products list when repository return products`() =
        runBlockingTest {
            // Given
            val products = listOf(
                Product(name = "Glasses"),
                Product(name = "Notebook"),
            )
            PowerMockito.doReturn(products).`when`(productsRepository).getProducts()

            // When
            val flow = getProductsUseCase()

            // Then
            flow.test {
                assertTrue(
                    awaitItem() is Resource.Loading
                )

                val successState = awaitItem()
                assertTrue(successState is Resource.Success)

                val emittedProducts = (successState as Resource.Success<List<Product>>).data
                assertTrue(emittedProducts.size == 2)
                assertEquals("Glasses", emittedProducts[0].name)
                assertEquals("Notebook", emittedProducts[1].name)

                awaitComplete()
            }
        }

    @Test
    fun `use case is emitting ApiErrorMessage when repository throw ErrorResponseMessageException`() =
        runBlockingTest {
            // Given
            given(productsRepository.getProducts()).willAnswer {
                throw ErrorResponseMessageException(statusCode = 400, message = "Error happened")
            }

            // When
            val flow = getProductsUseCase()

            // Then
            flow.test {
                assertTrue(
                    awaitItem() is Resource.Loading
                )

                val errorState = awaitItem()
                assertTrue(errorState is Resource.Error)

                val appError = (errorState as Resource.Error).error
                assertTrue(appError is AppError.ApiErrorMessage)

                val apiError = appError as AppError.ApiErrorMessage
                assertEquals(400, apiError.statusCode)
                assertEquals("Error happened", apiError.message)

                awaitComplete()
            }
        }

    @Test
    fun `use case is emitting NetworkError when repository throw IOException`() = runBlockingTest {
        // Given
        given(productsRepository.getProducts()).willAnswer {
            throw IOException()
        }

        // When
        val flow = getProductsUseCase()

        // Then
        flow.test {
            assertTrue(
                awaitItem() is Resource.Loading
            )

            val errorState = awaitItem()
            assertTrue(errorState is Resource.Error)

            val appError = (errorState as Resource.Error).error
            assertTrue(appError is AppError.NetworkError)

            awaitComplete()
        }
    }

    @Test
    fun `use case is emitting GeneralError when repository throw Throwable`() = runBlockingTest {
        // Given
        given(productsRepository.getProducts()).willAnswer {
            throw Throwable()
        }

        // When
        val flow = getProductsUseCase()

        // Then
        flow.test {
            assertTrue(
                awaitItem() is Resource.Loading
            )

            val errorState = awaitItem()
            assertTrue(errorState is Resource.Error)

            val appError = (errorState as Resource.Error).error
            assertTrue(appError is AppError.GeneralError)

            awaitComplete()
        }
    }
}