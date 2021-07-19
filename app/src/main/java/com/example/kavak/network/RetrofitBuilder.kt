package com.example.kavak.network

import com.example.kavak.BuildConfig
import com.example.kavak.models.BestSellersResponse
import com.example.kavak.models.CategoryBooksResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers


interface ApiService {

    @GET("books.json")
    suspend fun getCategoryBooks(): CategoryBooksResponse

    @Headers("Content.type: application/json")
    @GET("best_sellers.json")
    suspend fun getBestSellersBooks(): BestSellersResponse
}

object RetrofitBuilder {

    private const val BASE_URL = "https://raw.githubusercontent.com/ejgteja/files/main/"
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        .build()


    private var instance: ApiService? = null
    fun getInstance(): ApiService = instance ?: synchronized(this) {
        instance ?: fullResponse().also {
            instance = it
        }
    }

    private fun fullResponse(): ApiService {
        return getRetrofit().create(ApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}