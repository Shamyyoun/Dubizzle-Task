package com.example.dubizzletask.features.products.presentation.productsList

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.dubizzletask.R
import com.example.dubizzletask.core.BaseAndroidTest
import com.example.dubizzletask.core.MainActivity
import com.example.dubizzletask.core.enqueueResponse
import com.example.dubizzletask.di.AppModule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@HiltAndroidTest
@UninstallModules(AppModule::class)
class ProductsListFragmentTest : BaseAndroidTest() {
    @get:Rule(order = 3)
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun ensure_that_products_recycler_is_displayed_and_other_views_not_onAppLaunch() {
//        mockWebServer.enqueueResponse(200, "products-200.json")
//
//        // Assert products recycler is visible and other view not
//        onView(withId(R.id.rv_products)).check(matches(isDisplayed()))
//        onView(withId(R.id.pb_products)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.tv_products_empty_msg)).check(matches(not(isDisplayed())))
//        onView(withId(R.id.tv_products_error_msg)).check(matches(not(isDisplayed())))
    }

    @Test
    fun ensure_that_ProductDetailsFragment_is_displayed_when_clicking_on_product_item() {
        mockWebServer.enqueueResponse(200, "products-200.json")

        // Click on product item
        onView(withId(R.id.rv_products))
            .perform(
                actionOnItemAtPosition<ProductsAdapter.ViewHolder>(
                    0,
                    click()
                )
            )

        // Assert that details fragment appeared and rendered the product
        onView(withId(R.id.tv_product_name)).check(matches(withText("Notebook")))
        onView(withId(R.id.tv_product_price)).check(matches(withText("AED 5")))
    }
}