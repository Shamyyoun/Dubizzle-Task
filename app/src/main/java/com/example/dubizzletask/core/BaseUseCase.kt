package com.example.dubizzletask.core

import com.example.dubizzletask.common.AppError
import com.example.dubizzletask.common.Resource
import com.example.dubizzletask.common.exceptions.ErrorResponseMessageException
import kotlinx.coroutines.flow.FlowCollector
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseUseCase {

    protected suspend inline fun <F> FlowCollector<Resource<F>>.tryFlow(func: (FlowCollector<Resource<F>>) -> Unit) {
        try {
            func.invoke(this)
        } catch (e: CancellationException) {
          throw e
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    error = AppError.NetworkError
                )
            )
        } catch (e: ErrorResponseMessageException) {
            emit(
                Resource.Error(
                    error = AppError.ApiErrorMessage(
                        statusCode = e.statusCode,
                        message = e.message
                    )
                )
            )
        } catch (e: Throwable) {
            emit(
                Resource.Error(
                    error = AppError.GeneralError
                )
            )
        }
    }
}