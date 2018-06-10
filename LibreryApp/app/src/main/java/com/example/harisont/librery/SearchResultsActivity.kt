package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        recycler_view.layoutManager = LinearLayoutManager(this)
        val json = intent.getStringExtra("res")
        val gson = GsonBuilder().create()
        val searchResults = gson.fromJson(json, SearchResults::class.java)
        println("First book: " + searchResults.items[0].volumeInfo.title)
    }
}

// CLASSES USED TO PARSE JSON

class SearchResults(val items: List<Book>)

class Book(val id: String,
           val volumeInfo: VolumeInfo)

class VolumeInfo(val title: String,
                 val authors: List<String>,
                 val publisher: String,
                 val publishedDate: String,
                 val imageLinks:ImageLinks)

class ImageLinks(val smallThumbnail: String)
