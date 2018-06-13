package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_book_details.*
import kotlin.concurrent.thread

class ViewBookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_book_details)

        // GET EXTRAS
        val extras = intent.extras

        val id = extras.getString(CustomViewHolder.ID)
        val title = extras.getString(CustomViewHolder.TITLE)
        val authors = extras.getString(CustomViewHolder.AUTHORS)
        val publisher = extras.getString(CustomViewHolder.PUBLISHER)
        val publishedDate = extras.getString(CustomViewHolder.PUBLISHED_DATE)
        val thumbnailURL = extras.getString(CustomViewHolder.THUMBNAIL_URL)

        // UPDATE GUI
        book_title.text = title
        book_author.text = authors
        book_publisher.text = publisher
        book_year.text = publishedDate
        if (thumbnailURL != "") {
            try {   // TODO: issue #31
                Picasso.get().load(thumbnailURL).into(book_cover)
            } catch (e: IllegalArgumentException) {
                println("Image path is probably empty. A placeholder will be used instead.")
            }
        }
        else book_cover.setImageResource(R.drawable.sample_cover)

        save_button.setOnClickListener {
            val record = BookEntity(
                    id,
                    title,
                    authors,
                    publisher,
                    publishedDate,
                    thumbnailURL,
                    read_chbox.isChecked,
                    rating_bar.rating,
                    notes.toString())
        }
        thread {
            AppDB.getInstance(this)
        }.start()
    }
}
