package com.example.harisont.librery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_share.*
import okhttp3.*
import java.io.IOException
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
            val client = OkHttpClient()
            val request = Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build()
            if (CheckNetworkStatus.isNetworkAvailable(this)) {
                thread {
                    client.newCall(request).enqueue(object : Callback {  // cannot use .execute() in the UI thread
                        override fun onResponse(call: Call?, response: Response?) {
                            val json = response?.body()?.string()
                            println("Works like a charm!")
                            val gson = GsonBuilder().create()
                            val parsedJson = gson.fromJson(json, JsonResponse::class.java)
                            if(parsedJson.success==1)
                                runOnUiThread {
                                    Toast.makeText(this@ShareActivity, getString(R.string.posted),Toast.LENGTH_LONG).show()
                                }
                            else runOnUiThread {
                                Toast.makeText(this@ShareActivity, getString(R.string.post_failure), Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call?, e: IOException?) {
                            println("Epic fail!")
                            runOnUiThread {
                                Toast.makeText(this@ShareActivity, getString(R.string.post_failure), Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            } else Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_LONG).show()
        }
    }
}
