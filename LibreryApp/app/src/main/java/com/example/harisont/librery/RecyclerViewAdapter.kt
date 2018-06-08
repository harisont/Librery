package com.example.harisont.librery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row.view.*

class RecyclerViewAdapter(val BookList: SearchResults): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return BookList.books.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val row = layoutInflater.inflate(R.layout.row, parent, false)
        return CustomViewHolder(row)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder?.v?.title?.text = "Title"
        holder?.v?.author?.text = "Author"
        holder?.v?.cover?.setImageResource(R.drawable.sample_cover)
    }
}

class CustomViewHolder(val v: View): RecyclerView.ViewHolder(v) {

}