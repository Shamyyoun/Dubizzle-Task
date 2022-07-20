package com.example.dubizzletask.core

import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.function.ThrowingRunnable
import retrofit2.Response

internal fun mockErrorResponse(json: String = "", code: Int = 400): Response<Any> {
    return Response.error(code, json.toResponseBody("application/json".toMediaType()))
}

fun <T : Throwable> assertSuspendedThrows(expectedThrowable: Class<T>, func: suspend () -> Unit): T {
    return Assert.assertThrows(expectedThrowable) {
        runBlocking {
            func.invoke()
        }
    }
}