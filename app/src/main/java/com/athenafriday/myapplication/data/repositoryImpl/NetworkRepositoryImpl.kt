package com.athenafriday.myapplication.data.repositoryImpl

import com.athenafriday.myapplication.data.remote.ApiService
import com.athenafriday.myapplication.data.util.NetworkResult
import com.athenafriday.myapplication.domain.repository.NetworkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeout

class NetworkRepositoryImpl(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): NetworkRepository {
    private val cache = mutableMapOf<String, Any>()
    override fun <T> makeRequest(
        requestCall: suspend () -> T,
        cacheKey: String?,
        timeoutMs: Long
    ): Flow<NetworkResult<T>> = flow {
        cacheKey?.let{
            if (cache.contains(it)) {
                emit(NetworkResult.Success(cache[it] as T))
                return@flow
            }
        }
        try {
            val response = withTimeout(timeoutMs) { requestCall() }
            cacheKey?.let { cache[it] = response as Any }
            emit(NetworkResult.Success(response))
        } catch (e: Exception) {
            val message = "Error"
            emit(NetworkResult.Error(message))
        }
    }.flowOn(dispatcher)
}