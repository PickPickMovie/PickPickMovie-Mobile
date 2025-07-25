package com.dothebestmayb.pickpickmovie.data.storage

import com.dothebestmayb.pickpickmovie.data.model.AuthToken

interface SessionStorage {
    suspend fun get(): AuthToken?
    suspend fun set(info: AuthToken)

    suspend fun clear()
}
