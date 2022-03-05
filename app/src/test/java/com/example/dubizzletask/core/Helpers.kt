package com.example.dubizzletask.core

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

internal fun mockErrorResponse(json: String = "", code: Int = 400): Response<Any> {
    return Response.error(code, json.toResponseBody("application/json".toMediaType()))
}