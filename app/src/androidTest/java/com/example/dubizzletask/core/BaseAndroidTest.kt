package com.example.dubizzletask.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
abstract class BaseAndroidTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule(order = 2)
    val testCoroutineRule = TestCoroutineRule()

    @Before
    open fun setUp() {
        hiltRule.inject()
    }
}