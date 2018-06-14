package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
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
        recycler_view.adapter = RecyclerViewAdapter(searchResults.items)
        if (recycler_view.adapter.itemCount == 0) {
            Toast.makeText(this@SearchResultsActivity, R.string.no_matching_data, Toast.LENGTH_LONG).show()
            finish()
        }
    }
}
