package com.example.harisont.librery

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlin.concurrent.thread

class MainActivityFragment: Fragment() {

    private var db: AppDB? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        db = AppDB.getInstance(activity!!.applicationContext)
        val layout = inflater.inflate(R.layout.fragment_main, container, false)
        layout.recycler_view.layoutManager = LinearLayoutManager(activity)
        val read = arguments!!.getBoolean("read")
        thread {
            val bookList = db?.bookDAO()?.selectBookList(read)
            if (bookList != null)
                recycler_view.adapter = RecyclerViewAdapter(bookList)   // TODO: in UI thread
        }
        return layout
    }

}