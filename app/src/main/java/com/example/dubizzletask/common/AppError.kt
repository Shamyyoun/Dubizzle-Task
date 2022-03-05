package com.example.dubizzletask.common

sealed class AppError {
    object GeneralError : AppError()
    object NetworkError : AppError()
    data class ApiErrorMessage(val message: String, val statusCode: Int = 0) : AppError()
    data class ApiErrorResponse<T>(val model: T, val statusCode: Int = 0) : AppError()
}