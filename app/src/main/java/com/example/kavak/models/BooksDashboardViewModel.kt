package com.example.kavak.models

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kavak.network.BooksApiService
import com.example.kavak.utils.deserialize
import com.example.kavak.utils.serializedName
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class BooksDashboardViewModel(
    private val apiService: BooksApiService,
    private val dispatcher: CoroutineDispatcher,
    private val context: Context
) : ViewModel() {

    var categoryBooks = MutableLiveData<CategoryBookStatus>()
        private set

    var bestSellersBooks = MutableLiveData<BestSellersBookStatus>()
        private set

    private var categoryBooksDTO: MutableList<BookDTO.Book> = mutableListOf()

    enum class Genres {
        @SerializedName("Science")
        SCIENCE,

        @SerializedName("History")
        HISTORY,

        @SerializedName("Business")
        BUSINESS
    }

    fun init() = viewModelScope.launch(dispatcher) {
        categoryBooks.postValue(CategoryBookStatus.Loading)
        getCategoryBooks()
        getBestsellersBooks()
    }

    private suspend fun getCategoryBooks() {
        try {
            val categoryBooksResponse = apiService.getCategoryBooks()
            categoryBooks.postValue(CategoryBookStatus.Ready(categoryBooksResponse.toBookDTO()))
        } catch (e: Exception) {
            e.stackTrace
            categoryBooks.postValue(CategoryBookStatus.NetworkError("Tuvimos un problema de conexion, intenta mas tarde"))
        }
    }

    private suspend fun getBestsellersBooks() {
        try {
            handleBestSellersBooksResponse(apiService.getBestSellersBooks())
        } catch (e: Exception) {
            e.stackTrace
            bestSellersBooks.postValue(BestSellersBookStatus.NetworkError("Tuvimos un problema de conexion, intenta mas tarde"))
        }
    }

    private fun handleBestSellersBooksResponse(bestSellersBooksResponse: BestSellersResponse) {
        val bestSellerList: MutableList<BookDTO.Book> = mutableListOf()
        val bestSellers = bestSellersBooksResponse.results.bestSellers
        bestSellers.forEach { id ->
            categoryBooksDTO.firstOrNull { it.id == id }?.let {
                bestSellerList.add(it)
            }
        }
        bestSellersBooks.postValue(BestSellersBookStatus.Success(bestSellerList))
    }

    private fun CategoryBooksResponse.toBookDTO(): List<BookDTO> {
        val historyBooksDto = BookDTO(Genres.HISTORY.serializedName, mutableListOf())
        val scienceBooksDto = BookDTO(Genres.SCIENCE.serializedName, mutableListOf())
        val businessBookDTO = BookDTO(Genres.BUSINESS.serializedName, mutableListOf())
        results.books.forEach { book ->
            when (book.genre.deserialize<Genres>()) {
                Genres.BUSINESS -> addCategoryBook(businessBookDTO, book)
                Genres.HISTORY -> addCategoryBook(historyBooksDto, book)
                Genres.SCIENCE -> addCategoryBook(scienceBooksDto, book)
            }
        }
        return listOf(historyBooksDto, scienceBooksDto, businessBookDTO)
    }

    private fun addCategoryBook(categoryList: BookDTO, categoryBook: CategoryBooksResponse.Book) {
        categoryBook.toBookDTO().run {
            categoryList.booksList.add(this)
            categoryBooksDTO.add(this)
        }
    }

    private fun CategoryBooksResponse.Book.toBookDTO(): BookDTO.Book {
        fetchImage(img)
        return BookDTO.Book(
            id = isbn, title = title, author = author, description = description, img = img
        )
    }

    private fun fetchImage(url: String?) {
        url ?: return
        Picasso.with(context)
            .load(url)
            .fetch()
    }

    class Factory(
        private val dispatcher: CoroutineDispatcher,
        private val apiHelper: BooksApiService,
        private val context: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BooksDashboardViewModel(apiHelper, dispatcher, context) as T
        }
    }
}