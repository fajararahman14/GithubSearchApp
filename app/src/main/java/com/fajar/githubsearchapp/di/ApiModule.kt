package com.fajar.githubsearchapp.di

import com.fajar.githubsearchapp.BuildConfig
import com.fajar.githubsearchapp.api.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class ApiModule : ApiService {
    companion object{
        private var apiKey = BuildConfig.API_KEY
        private var baseUrl = BuildConfig.BASE_URL
        private val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("Authorization", "token $apiKey")
                .build()
            chain.proceed(requestHeaders)
        }
        private val client : OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val apiService : ApiService = retrofit.create(ApiService::class.java)
    }
}