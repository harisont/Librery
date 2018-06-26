package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.harisont.librery.db.BookEntity
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*
import kotlin.concurrent.thread
import okhttp3.OkHttpClient
import okhttp3.Request
import android.os.AsyncTask.execute




class RecommendationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val json = intent.getStringExtra("res")
        println("JSON from anonimalettori: $json")
        val gson = GsonBuilder().create()
        val recommendationsJson = gson.fromJson(json, Recommendations::class.java)
        thread {
            var recommendedBooks = mutableListOf<BookEntity>()
            if (recommendationsJson.success == 1 && CheckNetworkStatus.isNetworkAvailable(this)) {
                for (rec in recommendationsJson.data) {
                    val bookInfoFromGoogle = lookForBook(rec.libro)
                    if (bookInfoFromGoogle != null) {
                        recommendedBooks.add(BookEntity(
                                rec.libro,
                                bookInfoFromGoogle.volumeInfo.title,
                                bookInfoFromGoogle.volumeInfo.authors[0],   // TODO: join all authors to string (fun)
                                bookInfoFromGoogle.volumeInfo.publisher,
                                bookInfoFromGoogle.volumeInfo.publishedDate,
                                bookInfoFromGoogle.volumeInfo.imageLinks.smallThumbnail,
                                false,
                                rec.valutazione,
                                rec.commento))
                    }
                }
            } else {
                runOnUiThread {
                    Toast.makeText(this, R.string.not_connected, Toast.LENGTH_LONG).show()
                }
            }
            runOnUiThread {
                setContentView(R.layout.fragment_main)
                recycler_view.layoutManager = LinearLayoutManager(this)
                if (recommendedBooks.size == 0) {
                    Toast.makeText(this@RecommendationsActivity, R.string.no_matching_data, Toast.LENGTH_LONG).show()
                    finish()
                } else
                    recycler_view.adapter = RecyclerViewAdapter(recommendedBooks)
            }
        }
    }

    private fun lookForBook(id: String): Book? {
        val url = "https://www.googleapis.com/books/v1/volumes/$id"
        val client = OkHttpClient()
        val req = Request.Builder().url(url).build()
        val res = client.newCall(req).execute()
        val json = res.body()?.string()
        val gson = GsonBuilder().create()
        return gson.fromJson(json, Book::class.java)
    }
}
