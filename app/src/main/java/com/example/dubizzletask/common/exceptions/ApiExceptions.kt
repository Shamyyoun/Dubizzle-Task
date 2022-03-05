package com.example.dubizzletask.common.exceptions

data class ErrorResponseMessageException(val statusCode: Int, override val message: String = "") : Throwable()

object NoResponseException : Throwable()