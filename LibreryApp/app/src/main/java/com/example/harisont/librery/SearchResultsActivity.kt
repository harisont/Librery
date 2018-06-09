package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*

class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)
        recycler_view.layoutManager = LinearLayoutManager(this)
        val bundle = intent.getBundleExtra("resBundle")
        //var searchResults  = bundle.getParcelable<SearchResults>("res") as SearchResults
        //println(searchResults)
        //recycler_view.adapter = RecyclerViewAdapter(searchResults)
    }
}
