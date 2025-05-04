package com.example.artsyactivity.network

sealed interface ApiResult<out D, out E> {

    data class Success<out D>(val data: D) : ApiResult<D, Nothing>

    data class Error<out E : NetworkError>(val error: E) : ApiResult<Nothing, E>
}

// Empty placeholder for Errors
enum class NetworkError(val message: String = "") {
    PARSE_FAILED,
    REQUEST_TIMED_OUT(message = "Oops! Your request took too long to respond. Can you check your internet connection and try again later"),
    UNAUTHORIZED,
    ALREADY_AUTHENTICATED,
    CONFLICT,
    TOO_MANY_REQUEST,
    NO_INTERNET(message = "Looks like you’ve lost connection to the internet! Kindly ensure you're connected and give it another shot."),
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}

