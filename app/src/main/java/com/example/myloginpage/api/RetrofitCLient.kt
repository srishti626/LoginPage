package com.example.myloginpage.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCLient {
    private val retrofit by lazy{
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        val logger: HttpLoggingInterceptor =
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        val okkHttpclient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        Retrofit.Builder().baseUrl("http://restapi.adequateshop.com/")
            .client(okkHttpclient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    val apiInterface by lazy{
        retrofit.create(Api::class.java)
    }
}