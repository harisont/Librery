package com.example.harisont.librery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ShareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val extras = intent.extras
        val id = extras.getString("id")
        val rating = extras.getFloat("rating")
    }
}
