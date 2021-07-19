package com.example.kavak.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kavak.R
import com.example.kavak.models.BookDTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rv_best_seller_item.view.*

class BestSellerBooksAdapter(
    private val bestSellerList: List<BookDTO.Book>,
    private val context: Context
) :
    RecyclerView.Adapter<BestSellerBooksAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_best_seller_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bestSellerList[position], context)
    }

    override fun getItemCount(): Int = bestSellerList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(bestSellerBook: BookDTO.Book, context: Context) {
            itemView.book_title.text = bestSellerBook.title
            itemView.book_author.text = bestSellerBook.author
            Picasso.with(context).load(bestSellerBook.img).into(itemView.image_book_cover)
        }
    }
}