package com.example.kavak.models

sealed class CategoryBookStatus {
    object Loading : CategoryBookStatus()
    data class Ready(val categoryBooks: List<BookDTO>) : CategoryBookStatus()
    data class NetworkError(val message: String?) : CategoryBookStatus()
}

sealed class BestSellersBookStatus {
    data class Success(val bestSellersBooks: List<BookDTO.Book>) : BestSellersBookStatus()
    data class NetworkError(val stacktrace: String?) : BestSellersBookStatus()
    object Empty : BestSellersBookStatus()
}