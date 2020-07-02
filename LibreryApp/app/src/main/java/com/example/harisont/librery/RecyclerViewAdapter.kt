package com.example.harisont.librery

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harisont.librery.db.BookEntity
import com.example.harisont.librery.utils.Book
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*

class RecyclerViewAdapter(private val bookList: List<Any>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return try {
            bookList.count()
        } catch (e: NullPointerException) {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val row = layoutInflater.inflate(R.layout.row, parent, false)
        return CustomViewHolder(row)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val book = bookList[position]
        val title: String?
        val authors: String?
        val coverThumb: String?

        when (book) {
            is Book -> {
                holder.book = book  // public accessible book
                title = book.volumeInfo.title
                authors = listStringToString(book.volumeInfo.authors)
                coverThumb = if (book.volumeInfo.imageLinks != null) {  // handle missing thumbnails
                    book.volumeInfo.imageLinks.smallThumbnail
                } else ""
            }
            is BookEntity -> {
                holder.book = book
                title = book.title
                authors = book.authors
                coverThumb = if (book.thumbnailURL != null) {  // handle missing thumbnails
                    book.thumbnailURL
                } else ""

            }
            else -> {
                title = ""
                authors = ""
                coverThumb = ""
            }
        }
        val coverView = holder.v.cover
        try {
            Picasso.get().load(coverThumb+Math.random()).into(coverView) }  // some randomness to refresh images (wow!)
        catch (e: IllegalArgumentException) {
            println("Image path is probably empty. A placeholder will be used instead.")
        }
        holder.v.title?.text = title
        holder.v.author?.text = authors
        holder.v.cover?.setImageResource(R.drawable.new_sample_cover)
    }
}

class CustomViewHolder(val v: View, var book: Any? = null): RecyclerView.ViewHolder(v) {

    companion object {
        const val ID = "ID_K"
        const val TITLE = "TITLE_K"
        const val AUTHORS = "AUTHORS_K"
        const val PUBLISHER = "PUBLISHER_K"
        const val PUBLISHED_DATE = "PUBLISHED_DATE_K"
        const val THUMBNAIL_URL = "THUMBNAIL_URL_K"
        const val READ = "READ_K"
        const val RATING = "RATING_K"
        const val NOTES = "NOTES_K"
    }

    init {
        v.setOnClickListener {
            val i = Intent(v.context, ViewBookDetailsActivity::class.java)
            val e = Bundle()
            if (book is Book) {
                e.putString(ID, (book as Book)?.id)
                e.putString(TITLE, (book as Book)?.volumeInfo?.title)
                e.putString(AUTHORS, listStringToString((book as Book)?.volumeInfo?.authors))
                e.putString(PUBLISHER, (book as Book)?.volumeInfo?.publisher)
                e.putString(PUBLISHED_DATE, (book as Book)?.volumeInfo?.publishedDate)
                e.putString(THUMBNAIL_URL, (book as Book)?.volumeInfo?.imageLinks?.smallThumbnail)
            }
            else if (book is BookEntity) {
                e.putString(ID, (book as BookEntity)?.id)
                e.putString(TITLE, (book as BookEntity)?.title)
                e.putString(AUTHORS, (book as BookEntity)?.authors)
                e.putString(PUBLISHER, (book as BookEntity)?.publisher)
                e.putString(PUBLISHED_DATE, (book as BookEntity)?.publishedDate)
                e.putString(THUMBNAIL_URL, (book as BookEntity)?.thumbnailURL)
                e.putBoolean(READ, (book as BookEntity)?.read)
                e.putFloat(RATING, (book as BookEntity)?.rating)
                e.putString(NOTES, (book as BookEntity)?.notes)
            }
            i.putExtras(e)
            v.context.startActivity(i)
        }
    }
}

fun listStringToString(list: List<String>?): String? {
    return  list?.joinToString()
}