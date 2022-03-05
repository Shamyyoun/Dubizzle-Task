package com.example.dubizzletask.features.products.data.repository

import com.example.dubizzletask.common.exceptions.ErrorResponseMessageException
import com.example.dubizzletask.core.mockErrorResponse
import com.example.dubizzletask.features.products.data.remote.dto.ProductDto
import com.example.dubizzletask.features.products.data.remote.dto.ProductsApi
import com.example.dubizzletask.features.products.data.remote.dto.ProductsResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.powermock.api.mockito.PowerMockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class ProductsRepositoryImplTest {
    private val productsApi = PowerMockito.mock(ProductsApi::class.java)
    private val repository = ProductsRepositoryImpl(productsApi)

    @Test
    fun `getProducts() is returning products list if the api response is successful`() = runBlockingTest {
        // Given
        val response = Response.success(
            ProductsResponse(
                results = listOf(
                    ProductDto(name = "Notebook"),
                    ProductDto(name = "Glasses"),
                )
            )
        )
        PowerMockito.doReturn(response).`when`(productsApi).getProducts()

        // When
        val products = repository.getProducts()

        // Then
        assertTrue(products.size == 2)
        assertEquals("Notebook", products[0].name)
        assertEquals("Glasses", products[1].name)
    }

    @Test
    fun `getProducts() is returning empty list if the api response is empty`() = runBlockingTest {
        // Given
        val response = Response.success(
            ProductsResponse(
                results = emptyList()
            )
        )
        PowerMockito.doReturn(response).`when`(productsApi).getProducts()

        // When
        val products = repository.getProducts()

        // Then
        assertTrue(products.isEmpty())
    }

    @Test
    fun `getProducts() is throwing exception if the api response is 500`() = runBlockingTest {
        // Given
        val response = mockErrorResponse(code = 500)
        PowerMockito.doReturn(response).`when`(productsApi).getProducts()

        // When
        val exception = assertThrows(ErrorResponseMessageException::class.java) {
            runBlocking {
                repository.getProducts()
            }
        }

        // Then
        assertEquals(500, exception.statusCode)
    }

    @Test
    fun `getProducts() is throwing exception if the api response is 500 with message`() = runBlockingTest {
        // Given
        val json = "{\"message\": \"Unexpected error\"}"
        val response = mockErrorResponse(json = json, code = 400)
        PowerMockito.doReturn(response).`when`(productsApi).getProducts()

        // When
        val exception = assertThrows(ErrorResponseMessageException::class.java) {
            runBlocking {
                repository.getProducts()
            }
        }

        // Then
        assertEquals("Unexpected error", exception.message)
        assertEquals(400, exception.statusCode)
    }
}