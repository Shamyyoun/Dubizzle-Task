package com.example.dubizzletask.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dubizzletask.di.EndPointPort
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject

@ExperimentalCoroutinesApi
abstract class BaseAndroidTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    val testCoroutineRule = TestCoroutineRule()

    @JvmField
    @Inject
    @EndPointPort
    var endPointPort: Int = 0

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Before
    open fun setUp() {
        hiltRule.inject()
        mockWebServer.start(endPointPort)
    }

    @After
    open fun tearDown() {
        mockWebServer.shutdown()
    }
}