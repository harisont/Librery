package com.example.harisont.librery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.harisont.librery.db.BookEntity
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import kotlin.concurrent.thread

class RecommendationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val json = intent.getStringExtra("res")
        println("JSON from anonimalettori: $json")
        val gson = GsonBuilder().create()
        val recommendationsJson = gson.fromJson(json, Recommendations::class.java)
        if (recommendationsJson.success == 1 && CheckNetworkStatus.isNetworkAvailable(this)) {
            var recommendedBooks = mutableListOf<BookEntity>()
            for (rec in recommendationsJson.data) {
                val bookInfo = lookForBook(rec.libro)
                recommendedBooks.add(BookEntity(
                        rec.libro, // TODO: change constructor parameters
                        null,
                        null,
                        null,
                        null,
                        null,
                        false,
                        rec.valutazione,
                        rec.commento))
            }
        } else Toast.makeText(this, R.string.not_connected, Toast.LENGTH_LONG).show()
        // TODO: setContentView(R.layout.fragment_main)
        // TODO: pass book list to adapter (maybe cast mutable list to list?) like SearchResultsActivity
    }

    private fun lookForBook(id: String): Book? {
        val url = "https://www.googleapis.com/books/v1/volumes/$id"
        var parsedBook: Book? // TODO: fix!
        parsedBook = null
        if (CheckNetworkStatus.isNetworkAvailable(this)) {
            val client = OkHttpClient()
            val req = Request.Builder().url(url).build()
            thread {
                client.newCall(req).enqueue(object : Callback {  // cannot use .execute() in the UI thread
                    override fun onResponse(call: Call?, response: Response?) {
                        val json = response?.body()?.string()
                        println("Works like a charm!")
                        val gson = GsonBuilder().create()
                        parsedBook = gson.fromJson(json, Book::class.java)
                    }
                    override fun onFailure(call: Call?, e: IOException?) {
                        println("Epic fail!")
                        runOnUiThread {
                            Toast.makeText(this@RecommendationsActivity, getString(R.string.query_failure), Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        } else Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_LONG).show()
        return parsedBook
    }
}
