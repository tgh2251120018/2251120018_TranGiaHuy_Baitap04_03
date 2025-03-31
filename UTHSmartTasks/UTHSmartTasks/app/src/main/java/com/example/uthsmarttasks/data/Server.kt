package com.example.uthsmarttasks.data

import retrofit2.http.GET
import retrofit2.http.Path

interface Server {
    @GET("researchUTH/tasks")
    suspend fun getTasks(): APIAnswer

    @GET("researchUTH/tasks/{id}")
    suspend fun getTaskById(@Path("id") id: Int): Task
}