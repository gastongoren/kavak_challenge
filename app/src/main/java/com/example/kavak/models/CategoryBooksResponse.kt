package com.example.kavak.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryBooksResponse(
    @SerializedName("results")
    val results: Books,
) {
    @Keep
    data class Books(
        @SerializedName("books")
        val books: List<Book>
    )

    @Keep
    data class Book(
        @SerializedName("isbn")
        var isbn: String? = null,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("author")
        var author: String? = null,
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("genre")
        var genre: String? = null,
        @SerializedName("img")
        var img: String? = null,
        @SerializedName("imported")
        var imported: Boolean = false
    )
}