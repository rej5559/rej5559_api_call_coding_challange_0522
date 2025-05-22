package com.athenafriday.myapplication.data.remote

import com.athenafriday.myapplication.data.dto.ExampleDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("example/{id}")
    suspend fun getUser(@Path("id") id: String): ExampleDto
}