package com.example.harisont.librery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_book_details.*
import kotlin.concurrent.thread
import android.widget.TextView
import com.example.harisont.librery.db.AppDB
import com.example.harisont.librery.db.BookEntity
import com.example.harisont.librery.share.ShareActivity
import com.example.harisont.librery.utils.CheckNetworkStatus


class ViewBookDetailsActivity : AppCompatActivity() {

    private var db: AppDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        fun updateBook(record: BookEntity) {
            thread { db?.bookDAO()?.saveBookData(record) }
            runOnUiThread {
                Toast.makeText(this, R.string.added, Toast.LENGTH_LONG).show()
                finish()    // ugly way to go back without setting parent activity, which varies
            }
        }
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
        book_cover.setImageResource(R.drawable.new_sample_cover)
        if (thumbnailURL != "" && CheckNetworkStatus.isNetworkAvailable(this)) {
            try {
                Picasso.get().load(thumbnailURL).into(book_cover)
            } catch (e: IllegalArgumentException) {
                println("Image path is probably empty. A placeholder will be used instead.")
            }
        }
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
                if (db?.bookDAO()?.selectBook(id) == null)
                    updateBook(record)
                else {
                    runOnUiThread {
                        AlertDialog.Builder(this)
                                .setTitle(R.string.safe_upd_title)
                                .setMessage(R.string.safe_upd_question)
                                .setPositiveButton(R.string.yes) { _,_ ->
                                    updateBook(record)
                                }
                                .setNegativeButton(R.string.no, null)
                                .show()
                    }
                }
            }
        }

        del_button.setOnClickListener {
            thread {
                val toBeDeleted = db?.bookDAO()?.selectBook(id)
                if (toBeDeleted != null) {
                    runOnUiThread {
                        AlertDialog.Builder(this)
                                .setTitle(R.string.safe_del_title)
                                .setMessage(R.string.safe_del_question)
                                .setPositiveButton(R.string.yes) { _,_ ->
                                    thread { db?.bookDAO()?.deleteBookData(toBeDeleted) }
                                    runOnUiThread {
                                        Toast.makeText(this, R.string.deleted, Toast.LENGTH_LONG).show()
                                        finish()    // ugly way to go back without setting parent activity, which varies
                                    }
                                }
                                .setNegativeButton(R.string.no, null)
                                .show()
                    }
                }
                else {
                    runOnUiThread {
                        Toast.makeText(this, R.string.not_in_db, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        sh_button.setOnClickListener {
            val i = Intent(this, ShareActivity::class.java)
            val e = Bundle()
            e.putString("title", title)
            e.putString("authors", authors)
            e.putString("reason", notes.text.toString())
            startActivity(i.putExtras(e))
        }
    }
}
