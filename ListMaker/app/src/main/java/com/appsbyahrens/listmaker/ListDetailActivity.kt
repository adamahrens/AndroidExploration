package com.appsbyahrens.listmaker

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.widget.EditText

class ListDetailActivity : AppCompatActivity() {

    lateinit var taskList: TaskList
    lateinit var listItemsRecyclerView : RecyclerView
    lateinit var addTaskButton: FloatingActionButton

    private val TAG = ListDetailActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        taskList = intent.getParcelableExtra<TaskList>(ListActivity.INTENT_LIST_KEY)
        title = taskList.name

        Log.d(TAG, "Got $taskList from intent via Parcelable")
        Log.d(TAG, "TaskList Name is $title")

        listItemsRecyclerView = findViewById<RecyclerView>(R.id.list_items_recycler_view)
        listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(taskList)
        listItemsRecyclerView.layoutManager = LinearLayoutManager(this)

        addTaskButton = findViewById<FloatingActionButton>(R.id.add_task_button)

        addTaskButton.setOnClickListener { _ ->
            Log.d(TAG, "onClick")
            showAddTaskDialog()
        }
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(ListActivity.INTENT_LIST_KEY, taskList)

        val intent = Intent()
        intent.putExtras(bundle)

        // For passing back to ListActivity
        setResult(Activity.RESULT_OK, intent)

        super.onBackPressed()
    }

    private fun showAddTaskDialog() {
        val title = getString(R.string.task_to_add)
        val buttonTitle = getString(R.string.add_task)

        val builder = AlertDialog.Builder(this)
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(title)
        builder.setView(editText)
        builder.setPositiveButton(buttonTitle, { dialog, _ ->
            val taskName = editText.text.toString()
            taskList.tasks.add(taskName)

            val recyclerAdapter = listItemsRecyclerView.adapter as
                    ListItemsRecyclerViewAdapter
            recyclerAdapter.notifyItemInserted(taskList.tasks.size)
            Log.d(TAG, "User entered a task name of $taskName")
            dialog.dismiss()
        })

        builder.create().show()
    }
}
