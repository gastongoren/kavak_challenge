package com.example.kavak.models

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class BestSellersResponse(
    @SerializedName("results")
    @Expose
    val results: BestSellers
) {
    data class BestSellers(
        @SerializedName("best_sellers")
        val bestSellers: List<String>
    )
}
