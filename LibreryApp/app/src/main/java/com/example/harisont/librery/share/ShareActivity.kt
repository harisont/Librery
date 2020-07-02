package com.example.harisont.librery.share

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.harisont.librery.R
import kotlinx.android.synthetic.main.activity_share.*


class ShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val extras = intent.extras
        val title = extras.getString("title")
        val authors = extras.getString("authors")
        val defReason = extras.getString("reason")

        pub_notes.setText(defReason, TextView.BufferType.EDITABLE)

        post_button.setOnClickListener {
            val message = getString(R.string.message1) + title + getString(R.string.message2) + authors + getString(R.string.message3) + pub_notes.text
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }
            startActivity(sendIntent)
        }
    }
}
