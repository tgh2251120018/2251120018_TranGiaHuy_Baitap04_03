package com.example.uthsmarttasks.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    private const val BASE_URL = "https://amock.io/api/"

    val apiServer: Server by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Server::class.java)
    }
}