package com.example.dubizzletask.features.products.presentation.productDetails

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.dubizzletask.R
import com.example.dubizzletask.core.BaseAndroidTest
import com.example.dubizzletask.di.AppModule
import com.example.dubizzletask.features.products.data.FakeProductsData
import com.example.dubizzletask.features.products.data.remote.dto.ProductsResponse
import com.example.dubizzletask.features.products.data.remote.dto.toProduct
import com.example.dubizzletask.features.products.domain.models.Product
import com.example.dubizzletask.core.launchFragmentInHiltContainer
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProductDetailsFragmentTest : BaseAndroidTest() {
    @Inject
    lateinit var gson: Gson

    private lateinit var productInTest: Product

    @Before
    override fun setUp() {
        super.setUp()

        productInTest = gson.fromJson(
            FakeProductsData.productsJson,
            ProductsResponse::class.java
        ).results[3].toProduct()
    }

    @Test
    fun ensure_that_ProductDetailsFragment_is_displayed_and_renders_product_details() {
        launchFragmentInHiltContainer<ProductDetailsFragment>(
            fragmentArgs = bundleOf ("product" to productInTest)
        )

        onView(withId(R.id.tv_product_name)).check(matches(withText(productInTest.name)))
        onView(withId(R.id.tv_product_price)).check(matches(withText(productInTest.price)))
    }
}