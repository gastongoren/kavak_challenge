package com.example.kavak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kavak.adapters.BestSellerBooksAdapter
import com.example.kavak.adapters.CategoryBooksAdapter
import com.example.kavak.models.BestSellersBookStatus
import com.example.kavak.models.BookDTO
import com.example.kavak.models.BooksDashboardViewModel
import com.example.kavak.models.CategoryBookStatus
import com.example.kavak.network.BooksApiService
import com.example.kavak.network.RetrofitBuilder
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.Dispatchers


class BooksDashboard : AppCompatActivity() {

    private val viewModel: BooksDashboardViewModel by lazy {
        ViewModelProvider(
            this,
            BooksDashboardViewModel.Factory(
                dispatcher = Dispatchers.IO,
                apiHelper = BooksApiService(RetrofitBuilder.getInstance()),
                context = this
            )
        ).get(BooksDashboardViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    override fun onResume() {
        super.onResume()
        handleViewModelResponse()
    }

    private fun handleViewModelResponse() {
        viewModel.categoryBooks.observe(this, { status ->
            when (status) {
                is CategoryBookStatus.Loading -> showLoading(true)
                is CategoryBookStatus.Ready -> showCategoryBooks(status.categoryBooks)
                is CategoryBookStatus.NetworkError -> showNetworkError()
            }
        })

        viewModel.bestSellersBooks.observe(this, { status ->
            when (status) {
                is BestSellersBookStatus.Success -> showBestSellersBooks(status.bestSellersBooks)
                is BestSellersBookStatus.NetworkError -> showNetworkError()
                is BestSellersBookStatus.Empty -> rv_best_seller.isVisible = false
            }
        })
        viewModel.init()
    }

    private fun showLoading(visibility: Boolean) {
        progress_circular.isVisible = visibility
    }

    private fun showNetworkError() {
        showLoading(false)
        network_error_view.isVisible = true
        viewModel.init()
    }

    private fun showCategoryBooks(categoryBooks: List<BookDTO>) {
        showLoading(false)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(
            rv_books.context,
            linearLayoutManager.orientation
        )
        rv_books.apply {
            isVisible = true
            adapter = CategoryBooksAdapter(categoryBooks, context)
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun showBestSellersBooks(bestSellers: List<BookDTO.Book>) {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_best_seller.apply {
            isVisible = true
            adapter = BestSellerBooksAdapter(bestSellers, context)
            layoutManager = linearLayoutManager
        }
    }
}