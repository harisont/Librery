package com.example.harisont.librery

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row.view.*

class RecyclerViewAdapter(private val BookList: SearchResults): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return try {
            BookList.items.count()
        } catch (e: NullPointerException) {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val row = layoutInflater.inflate(R.layout.row, parent, false)
        return CustomViewHolder(row)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val book = BookList.items[position]
        holder?.book = book  // public accessible book
        val title = book.volumeInfo.title
        val authors = listStringToString(book.volumeInfo.authors)
        val coverThumb: String
        coverThumb = if (book.volumeInfo.imageLinks != null) {  // handle missing thumbnails, causing frequent crashes
            book.volumeInfo.imageLinks.smallThumbnail
        } else ""
        val coverView = holder?.v?.cover
        try {
            Picasso.get().load(coverThumb).into(coverView) }
        catch (e: IllegalArgumentException) {
            println("Image path is probably empty. A placeholder will be used instead.")
        }
        holder?.v?.title?.text = title
        holder?.v?.author?.text = authors
        holder?.v?.cover?.setImageResource(R.drawable.sample_cover)
    }
}

class CustomViewHolder(val v: View, var book: Book? = null): RecyclerView.ViewHolder(v) {

    companion object {
        const val ID = "ID_K"
        const val TITLE = "TITLE_K"
        const val AUTHORS = "AUTHORS_K"
        const val PUBLISHER = "PUBLISHER_K"
        const val PUBLISHED_DATE = "PUBLISHED_DATE_K"
        const val THUMBNAIL_URL = "THUMBNAIL_URL_K"
    }

    init {
        v.setOnClickListener {
            val i = Intent(v.context, ViewBookDetailsActivity::class.java)
            val e = Bundle()
            e.putString(ID, book?.id)
            e.putString(TITLE, book?.volumeInfo?.title)
            e.putString(AUTHORS, listStringToString(book?.volumeInfo?.authors))
            e.putString(PUBLISHER, book?.volumeInfo?.publisher)
            e.putString(PUBLISHED_DATE, book?.volumeInfo?.publishedDate)
            e.putString(THUMBNAIL_URL, book?.volumeInfo?.imageLinks?.smallThumbnail)
            i.putExtras(e)
            v.context.startActivity(i)
        }
    }
}

fun listStringToString(list: List<String>?): String? {
    return  list?.joinToString()
}