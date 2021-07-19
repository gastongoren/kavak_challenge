package com.example.kavak.network

import com.example.kavak.models.BestSellersResponse
import com.example.kavak.models.CategoryBooksResponse

/*
    Pass the RetrofitBuilder.buildService() here
    This class is necessary because without it we should create the Retrofit object thus generating
    a dependency on the class
 */
class BooksApiService(private val apiService: ApiService) {
    suspend fun getCategoryBooks(): CategoryBooksResponse = apiService.getCategoryBooks()
    suspend fun getBestSellersBooks(): BestSellersResponse =
        apiService.getBestSellersBooks()
}