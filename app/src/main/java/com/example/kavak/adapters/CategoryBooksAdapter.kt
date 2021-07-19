package com.example.kavak.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kavak.R
import com.example.kavak.models.BookDTO
import kotlinx.android.synthetic.main.rv_book_content.view.*

class CategoryBooksAdapter(
    private val categoryBooks: List<BookDTO>,
    private val context: Context
) :
    RecyclerView.Adapter<CategoryBooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_book_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryBooks[position], context)
    }

    override fun getItemCount(): Int = categoryBooks.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(book: BookDTO, context: Context) {
            val linearLayoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemView.rv_books.apply {
                layoutManager = linearLayoutManager
                adapter = BooksAdapter(book.booksList, context)
            }
            itemView.book_genre.text = book.genre
            itemView.book_quantity.text =
                context.getString(R.string.book_quantity, book.booksList.size.toString())
        }
    }
}