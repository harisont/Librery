package com.example.harisont.librery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_search.*
import okhttp3.*
import java.io.IOException

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_button.setOnClickListener {
            advancedSearch(null, null, null, null)
        }
    }

    private fun advancedSearch(isbn: String?, intitle: String?, inauthor: String?, inpublisher: String?) {
        val url = "https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC"
        val client = OkHttpClient()
        val req = Request.Builder().url(url).build()
        client.newCall(req).enqueue(object: Callback {  // cannot use .execute() in the UI thread
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                // println(body)
                println("Works like a charm!")
            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Epic fail!")
            }
        })
    }
}
