package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_share.*
import kotlinx.android.synthetic.main.activity_view_book_details.*

class ShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val extras = intent.extras
        val id = extras.getString("id")
        val defRating = extras.getFloat("def_rating")
        val defNotes = extras.getString("def_notes")

        pub_rating_bar.rating = defRating
        pub_notes.setText(defNotes, TextView.BufferType.EDITABLE)
    }
}
