package com.example.harisont.librery

import android.content.Intent
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
        val title = book.volumeInfo.title
        val authorList: List<String>?
        authorList = book.volumeInfo.authors
        val authors = authorList?.joinToString()    // kotlin magic to just join to string if not null
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

class CustomViewHolder(val v: View): RecyclerView.ViewHolder(v) {
    init {
        v.setOnClickListener {
            val i = Intent(v.context, ViewBookDetailsActivity::class.java)
            v.context.startActivity(i)
        }
    }
}