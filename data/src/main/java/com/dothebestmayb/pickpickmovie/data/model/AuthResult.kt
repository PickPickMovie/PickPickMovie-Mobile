package com.dothebestmayb.pickpickmovie.data.model

sealed class AuthResult<T>(val data: T? = null) {

    class Authorized<T>(data: T? = null) : AuthResult<T>(data)
    class Unauthorized<T> : AuthResult<T>()
    class UnknownError<T> : AuthResult<T>()
    class Conflict<T>(val reason: String) : AuthResult<T>()
}
