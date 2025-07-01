package com.dothebestmayb.pickpickmovie.data.auth.local.storage

import com.dothebestmayb.pickpickmovie.data.model.AuthToken

interface SessionStorage {
    suspend fun get(): AuthToken?
    suspend fun set(info: AuthToken?)
}
