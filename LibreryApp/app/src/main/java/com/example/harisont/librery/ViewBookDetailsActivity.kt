package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_book_details.*
import kotlin.concurrent.thread
import android.widget.TextView
import android.R.drawable.edit_text
import android.widget.EditText



class ViewBookDetailsActivity : AppCompatActivity() {

    private var db: AppDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_book_details)

        db = AppDB.getInstance(this)

        // GET EXTRAS
        val extras = intent.extras

        val id = extras.getString(CustomViewHolder.ID)
        val title = extras.getString(CustomViewHolder.TITLE)
        val authors = extras.getString(CustomViewHolder.AUTHORS)
        val publisher = extras.getString(CustomViewHolder.PUBLISHER)
        val publishedDate = extras.getString(CustomViewHolder.PUBLISHED_DATE)
        val thumbnailURL = extras.getString(CustomViewHolder.THUMBNAIL_URL)
        val read = extras.getBoolean(CustomViewHolder.READ)
        val rating = extras.getFloat(CustomViewHolder.RATING)
        val notesStr = extras.getString(CustomViewHolder.NOTES)

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
        notes.setText(notesStr, TextView.BufferType.EDITABLE)
        read_chbox.isChecked = read
        rating_bar.rating = rating

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
                    notes.text.toString())
            thread {
                db?.bookDAO()?.saveBookData(record)
                runOnUiThread {
                    Toast.makeText(this, R.string.added, Toast.LENGTH_LONG).show()
                }
                println("RECORD COUNT:"+db?.bookDAO()?.selectAll()?.size)
            }
        }

        del_button.setOnClickListener {
            thread {
                val toBeDeleted = db?.bookDAO()?.selectBook(id)
                if (toBeDeleted != null) {
                    // TODO: add dialog
                    db?.bookDAO()?.deleteBookData(toBeDeleted)
                    runOnUiThread {
                        Toast.makeText(this, R.string.deleted, Toast.LENGTH_LONG).show()
                    }
                }
                else {  // TODO: check why is not functioning
                    runOnUiThread {
                        Toast.makeText(this, R.string.not_in_db, Toast.LENGTH_LONG).show()
                    }
                }
                println("RECORD COUNT:" + db?.bookDAO()?.selectAll()?.size)
            }
        }
    }
}
