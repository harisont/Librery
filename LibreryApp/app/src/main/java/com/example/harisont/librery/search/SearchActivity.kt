package com.example.harisont.librery.search

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.harisont.librery.utils.CheckNetworkStatus
import com.example.harisont.librery.R
import kotlinx.android.synthetic.main.activity_search.*
import okhttp3.*
import java.io.IOException
import kotlin.concurrent.thread

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_button.setOnClickListener {
             advancedSearch(
                    search_by_isbn.text.toString(),
                    search_by_title.text.toString(),
                    search_by_author.text.toString(),
                    search_by_publisher.text.toString())
        }
    }

    private fun fetchBooks(url:String) {
        if (CheckNetworkStatus.isNetworkAvailable(this)) {
            val client = OkHttpClient()
            val req = Request.Builder().url(url).build()
            thread {
                client.newCall(req).enqueue(object : Callback {  // cannot use .execute() in the UI thread
                    override fun onResponse(call: Call?, response: Response?) {
                        val json = response?.body()?.string()
                        println("Works like a charm!")
                        startActivity(Intent(this@SearchActivity, SearchResultsActivity::class.java)
                                .putExtra("res", json))     // search results are sent to the new activity as JSON
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        println("Epic fail!")
                        runOnUiThread {
                            Toast.makeText(this@SearchActivity, getString(R.string.query_failure), Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        } else Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_LONG).show()
    }

    private fun advancedSearch(isbnCode: String, title: String, author: String, publisher: String) {
        val isbn = if (isbnCode != "") "isbn:$isbnCode" else ""
        val intitle = if (title != "") "intitle:$title" else ""
        val inauthor = if (author != "") "inauthor:$author" else ""
        val inpublisher = if (publisher != "") "inpublisher:$publisher" else ""
        val searchParameters = "q=$isbn+$intitle+$inauthor+$inpublisher"
        if (searchParameters == "q=+++") {  // if there are no search parameters (check could be improved)
            Toast.makeText(this, getString(R.string.fill_fields_toast), Toast.LENGTH_LONG).show()
        }
        else {
            val url = "https://www.googleapis.com/books/v1/volumes/?" +
                    searchParameters +
                    "&projection=lite"
            println("URL: $url")
            fetchBooks(url)
        }
    }
}
