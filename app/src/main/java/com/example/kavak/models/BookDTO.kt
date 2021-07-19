package com.example.kavak.models

data class BookDTO(
    val genre: String,
    val booksList: MutableList<Book>
) {
    data class Book(
        val id: String?,
        val title: String?,
        val author: String?,
        val description: String?,
        val img: String?
    )
}