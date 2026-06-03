package com.example.catalogapp.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

object RetrofitClient {
    // 10.0.2.2 es la IP para localhost desde el emulador de Android
    private const val BASE_URL = "http://35.255.161.52:3000/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val locale = Locale.getDefault()
            // locale.language es "es", locale.country es "MX". langTag queda como "es-MX" o "en-US".
            val langTag = if (locale.country.isNotEmpty()) {
                "${locale.language}-${locale.country}"
            } else {
                locale.language
            }
            
            val request = chain.request().newBuilder()
                .addHeader("Accept-Language", langTag)
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: CatalogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatalogApiService::class.java)
    }
}
