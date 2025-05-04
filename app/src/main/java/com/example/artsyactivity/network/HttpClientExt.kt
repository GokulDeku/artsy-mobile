package com.example.artsyactivity.network

import android.util.Log
import com.example.artsyactivity.data.network.models.response.ErrorMessage
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T, NetworkError> {
    val response = try {
        apiCall()
    } catch (e: SerializationException) {
        Log.d("VIJ", "In serialization ${e.message}")
        return ApiResult.Error(NetworkError.SERIALIZATION)
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        Log.d("VIJ", "In Unresolved address")
        return ApiResult.Error(NetworkError.NO_INTERNET)
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("VIJ", "In normal exception ${e}")
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
            Log.d("VIJ", "Response body is: $body")
            if (body != null) {
                ApiResult.Success(body)
            } else {
                Log.d("VIJ", "In else block")
                ApiResult.Error(NetworkError.PARSE_FAILED)
            }
        }
        400, 404, 500 -> {
            val body = response.errorBody()?.string()
            val error = Json.decodeFromString<ErrorMessage>(body.toString())
            val networkError = NetworkError.UNKNOWN.apply {
                message = error.message
            }
            ApiResult.Error(networkError)
        }
        401 -> ApiResult.Error(NetworkError.UNAUTHORIZED)
        403 -> ApiResult.Error(NetworkError.ALREADY_AUTHENTICATED)
        408 -> ApiResult.Error(NetworkError.REQUEST_TIMED_OUT)
        409 -> ApiResult.Error(NetworkError.CONFLICT)
        413 -> ApiResult.Error(NetworkError.PAYLOAD_TOO_LARGE)
        429 -> ApiResult.Error(NetworkError.TOO_MANY_REQUEST)
        in 501..599 -> ApiResult.Error(NetworkError.SERVER_ERROR)
        else -> ApiResult.Error(NetworkError.UNKNOWN)
    }
}