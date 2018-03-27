package com.appsbyahrens.listmaker

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.util.Log
import android.widget.EditText

class ListActivity : AppCompatActivity() {

    lateinit var listsRecyclerView: RecyclerView
    lateinit var addListButton: FloatingActionButton

    private val TAG = ListActivity::class.java.simpleName

    private val dataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        Log.d(TAG, "onCreate called in ListActivity")

        addListButton = findViewById<FloatingActionButton>(R.id.add_list_button)
        listsRecyclerView = findViewById<RecyclerView>(R.id.lists_recycler_view)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)

        val lists = dataManager.getTaskLists()
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists)

        addListButton.setOnClickListener { view ->
            Log.d(TAG, "onClick")
            showAddListDialog()
        }
    }

    private fun showAddListDialog() {
        val title = getString(R.string.name_of_list)
        val buttonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(title)
        builder.setView(editText)
        builder.setPositiveButton(buttonTitle, { dialog, i ->
            val listName = editText.text.toString()
            val list = TaskList(listName, ArrayList<String>())
            dataManager.save(list)

            val adapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            adapter.addTaskList(list)

            Log.d(TAG, "User entered a list name of $listName")
            dialog.dismiss()
        })

        builder.create().show()
    }
}
