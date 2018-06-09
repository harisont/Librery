package com.example.harisont.librery

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_main.*
import okhttp3.*
import java.io.IOException

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        search_button.setOnClickListener {
            val json = advancedSearch(
                    search_by_isbn.text.toString(),
                    search_by_title.text.toString(),
                    search_by_author.text.toString(),
                    search_by_publisher.text.toString())
        }
    }

    private fun fetchBooks(url:String) {
        val client = OkHttpClient()
        val req = Request.Builder().url(url).build()
        client.newCall(req).enqueue(object: Callback {  // cannot use .execute() in the UI thread
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                println("Works like a charm!")
                val gson = GsonBuilder().create()
                val searchResults = gson.fromJson(json, SearchResults::class.java)
            }
            override fun onFailure(call: Call?, e: IOException?) {
                println("Epic fail!")
            }
        })
        //startActivity(Intent(this, SearchResultsActivity::class.java))
    }

    private fun advancedSearch(isbnCode: String, title: String, author: String, publisher: String) {
        //val apiKey = "AIzaSyDWQIKMYe6760oFGDbNli8lBVt6IYxga7g"
        val isbn = if (isbnCode != "") "isbn:$isbnCode" else ""
        val intitle = if (title != "") "intitle:$title" else ""
        val inauthor = if (author != "") "inauthor:$author" else ""
        val inpublisher = if (publisher != "") "inpublisher:$publisher" else ""
        val url = "https://www.googleapis.com/books/v1/volumes/?" +
                "q=$isbn+$intitle+$inauthor+$inpublisher" +
                "&projection=lite"
        fetchBooks(url)
    }
}

class SearchResults(val items: List<Book>)

class Book(val id: String,
           val volumeInfo: VolumeInfo)

class VolumeInfo(val title: String,
                 val authors: List<String>,
                 val publisher: String,
                 val publishedDate: String,
                 val imageLinks:ImageLinks)

class ImageLinks(val smallThumbnail: String)
