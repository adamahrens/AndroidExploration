package com.appsbyahrens.listmaker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

class ListActivity : AppCompatActivity() {

    lateinit var listsRecyclerView: RecyclerView

    private val TAG = ListActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        Log.d(TAG, "onCreate called in ListActivity")

        listsRecyclerView = findViewById<RecyclerView>(R.id.lists_recycler_view)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter()
    }
}
