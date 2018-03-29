package com.appsbyahrens.listmaker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ListDetailActivity : AppCompatActivity() {

    lateinit var taskList: TaskList

    private val TAG = ListDetailActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        taskList = intent.getParcelableExtra<TaskList>(ListActivity.INTENT_LIST_KEY)
        title = taskList.name

        Log.d(TAG, "Got $taskList from intent via Parcelable")
        Log.d(TAG, "TaskList Name is $title")
    }
}
