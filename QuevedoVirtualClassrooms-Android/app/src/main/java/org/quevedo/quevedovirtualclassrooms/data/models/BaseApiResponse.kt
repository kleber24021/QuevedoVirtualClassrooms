package org.quevedo.quevedovirtualclassrooms.data.models

import android.util.Log
import retrofit2.Response
import java.lang.Exception

abstract class BaseApiResponse {
    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<R>,
        transform: (R) -> T
    ): NetworkResult<T>{
        try {
            val response = apiCall()
            if (response.isSuccessful){
                val body = response.body()
                body?.let{
                    return NetworkResult.Success(transform(body))
                }
            }
            return error("${response.code()} ${response.message()}")
        }catch (e: Exception){
            Log.e("ERROR", e.message.toString(), e)
            return error(e.message ?: e.toString())
        }
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString(), e)
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("API FAILED" + errorMessage)
}

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)
}