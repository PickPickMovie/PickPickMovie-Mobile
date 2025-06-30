package com.dothebestmayb.pickpickmovie.data.session

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}
