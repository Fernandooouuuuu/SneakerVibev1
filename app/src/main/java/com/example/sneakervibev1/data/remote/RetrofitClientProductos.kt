package com.example.sneakervibev1.data.remote

import com.example.sneakervibev1.data.remote.api.ProductoApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientProductos {

    private const val BASE_URL = "http://98.85.134.254:8081/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ProductoApiService by lazy {
        retrofit.create(ProductoApiService::class.java)
    }
}