package com.example.kavak.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kavak.R
import com.example.kavak.models.BookDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_book_item.view.*

class BooksAdapter(private val bookList: List<BookDTO.Book>, private val context: Context) :
    RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_book_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookList[position], context)
    }

    override fun getItemCount(): Int = bookList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(book: BookDTO.Book, context: Context) {
            itemView.book_author.text = book.author
            itemView.book_title.text = book.title
            itemView.book_description.text = book.description
            Picasso.with(context)
                .load(book.img)
                .fit().centerInside()
                .into(itemView.image_book_cover)
        }
    }
}