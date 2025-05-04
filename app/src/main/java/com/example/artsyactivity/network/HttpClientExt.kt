package com.example.artsyactivity.network

import android.util.Log
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T, NetworkError> {
    val response = try {
        apiCall()
    } catch (e: SerializationException) {
        return ApiResult.Error(NetworkError.SERIALIZATION)
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return ApiResult.Error(NetworkError.NO_INTERNET)
    } catch (e: Exception) {
        e.printStackTrace()
        if (e is CancellationException) throw e
        return ApiResult.Error(NetworkError.UNKNOWN)
    }
    Log.d("VIJ", "response: $response")
    return responseToResult(response)
}

inline fun <reified T> responseToResult(response: Response<T>): ApiResult<T, NetworkError> {
    return when (response.code()) {
        in 200..299 -> {
            val body = response.body()
            if (body != null) {
                ApiResult.Success(body)
            } else {
                ApiResult.Error(NetworkError.PARSE_FAILED)
            }
        }
        401 -> ApiResult.Error(NetworkError.UNAUTHORIZED)
        403 -> ApiResult.Error(NetworkError.ALREADY_AUTHENTICATED)
        408 -> ApiResult.Error(NetworkError.REQUEST_TIMED_OUT)
        409 -> ApiResult.Error(NetworkError.CONFLICT)
        413 -> ApiResult.Error(NetworkError.PAYLOAD_TOO_LARGE)
        429 -> ApiResult.Error(NetworkError.TOO_MANY_REQUEST)
        in 500..599 -> ApiResult.Error(NetworkError.SERVER_ERROR)
        else -> ApiResult.Error(NetworkError.UNKNOWN)
    }
}