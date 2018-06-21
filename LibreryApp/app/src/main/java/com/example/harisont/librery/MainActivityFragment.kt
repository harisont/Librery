package com.example.harisont.librery

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.harisont.librery.db.AppDB
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
                activity!!.runOnUiThread {
                    recycler_view.adapter = RecyclerViewAdapter(bookList)
                }
        }
        return layout
    }

    override fun onResume() {  
        super.onResume()
        thread {
            val read = arguments!!.getBoolean("read")
            val bookList = db?.bookDAO()?.selectBookList(read)
            if (bookList != null)
                activity!!.runOnUiThread {
                    recycler_view.adapter = RecyclerViewAdapter(bookList)
                }
        }
    }
}