package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_share.*
import okhttp3.OkHttpClient
import okhttp3.FormBody
import okhttp3.Request
import kotlin.concurrent.thread


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

        val url = "http://anonimalettori.altervista.org/db/insert.php"

        post_button.setOnClickListener {
            val formBody = FormBody.Builder()
                    .add("libro", id)
                    .add("commento", pub_notes.text.toString())
                    .add("valutazione", pub_rating_bar.rating.toString())
                    .build()
            println("PARAMETRI: ${formBody.value(0)}")
            thread {
                val client = OkHttpClient()
                val request = Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build()
                println("RICHIESTA: $request")

                val response = client.newCall(request).execute()
                println("RESPONSO: "+response.body()?.string())
            }
        }
    }
}
