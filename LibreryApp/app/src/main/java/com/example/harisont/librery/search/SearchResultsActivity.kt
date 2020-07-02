package com.example.harisont.librery.search

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.harisont.librery.R
import com.example.harisont.librery.RecyclerViewAdapter
import com.example.harisont.librery.utils.SearchResults
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_main.*

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        val json = intent.getStringExtra("res")
        val gson = GsonBuilder().create()
        val searchResults = gson.fromJson(json, SearchResults::class.java)
        recycler_view.layoutManager = LinearLayoutManager(this)
        if (searchResults.items == null) {
            Toast.makeText(this@SearchResultsActivity, R.string.no_matching_data, Toast.LENGTH_LONG).show()
            finish()
        }
        else
            recycler_view.adapter = RecyclerViewAdapter(searchResults.items)
    }
}
