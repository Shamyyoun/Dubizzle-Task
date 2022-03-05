package com.example.dubizzletask.features.products.presentation

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.dubizzletask.R
import com.example.dubizzletask.core.BaseAndroidTest
import com.example.dubizzletask.core.MainActivity
import com.example.dubizzletask.di.AppModule
import com.example.dubizzletask.features.products.data.FakeProductsData
import com.example.dubizzletask.features.products.data.remote.dto.ProductsResponse
import com.example.dubizzletask.features.products.data.remote.dto.toProduct
import com.example.dubizzletask.features.products.domain.models.Product
import com.example.dubizzletask.features.products.presentation.productsList.ProductsAdapter
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProductsEndToEndTest : BaseAndroidTest() {
    @get:Rule(order = 2)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var gson: Gson

    private val productInTestIndex = 4
    private lateinit var productInTest: Product

    @Before
    override fun setUp() {
        super.setUp()

        productInTest = gson.fromJson(
            FakeProductsData.productsJson,
            ProductsResponse::class.java
        ).results[productInTestIndex].toProduct()
    }

    @Test
    fun ensure_that_ProductDetailsFragment_is_displayed_when_clicking_on_product_item() {
        // Click on product item
        onView(withId(R.id.rv_products))
            .perform(actionOnItemAtPosition<ProductsAdapter.ViewHolder>(productInTestIndex, click()))

        // Assert that details fragment appeared and rendered the product
        onView(withId(R.id.tv_product_name)).check(matches(withText(productInTest.name)))
        onView(withId(R.id.tv_product_price)).check(matches(withText(productInTest.price)))
    }

    @Test
    fun ensure_that_ProductsListFragment_is_displayed_again_after_pressing_back() {
        // Click on product item
        onView(withId(R.id.rv_products))
            .perform(actionOnItemAtPosition<ProductsAdapter.ViewHolder>(productInTestIndex, click()))

        // Assert that details fragment appeared and rendered the product
        onView(withId(R.id.tv_product_name)).check(matches(withText(productInTest.name)))

        // Go back
        Espresso.pressBack()

        // Assert that list fragment appeared again
        onView(withId(R.id.rv_products)).check(matches(isDisplayed()))
    }
}