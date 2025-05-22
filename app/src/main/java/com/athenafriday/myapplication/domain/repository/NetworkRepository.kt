package com.athenafriday.myapplication.domain.repository

import com.athenafriday.myapplication.data.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun <T> makeRequest(
        requestCall: suspend () -> T,
        cacheKey: String? = null,
        timeoutMs: Long = 15000
    ): Flow<NetworkResult<T>>
}