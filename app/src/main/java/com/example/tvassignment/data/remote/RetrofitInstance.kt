package com.example.tvassignment.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://newsapi.org/"

    val api: NewsApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpProvider.client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApiService::class.java)
}