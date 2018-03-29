package com.appsbyahrens.listmaker

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

class ListActivity : AppCompatActivity(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    companion object {
        val INTENT_LIST_KEY = "IntentToListDetailActivity"
        val LIST_DETAIL_REQUEST_CODE = 666
    }

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
        listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)

        addListButton.setOnClickListener { _ ->
            Log.d(TAG, "onClick")
            showAddListDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                dataManager.save(it.getParcelableExtra(INTENT_LIST_KEY))
                updateLists()
            }
        }
    }

    override fun listItemClicked(list: TaskList) {
        showListDetailActivity(list)
    }

    private fun updateLists() {
        val lists = dataManager.getTaskLists()
        listsRecyclerView.adapter =
                ListSelectionRecyclerViewAdapter(lists, this)

    }

    private fun showAddListDialog() {
        val title = getString(R.string.name_of_list)
        val buttonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val editText = EditText(this)
        editText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(title)
        builder.setView(editText)
        builder.setPositiveButton(buttonTitle, { dialog, _ ->
            val listName = editText.text.toString()
            val list = TaskList(listName)
            dataManager.save(list)

            val adapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            adapter.addTaskList(list)

            Log.d(TAG, "User entered a list name of $listName")
            dialog.dismiss()
            showListDetailActivity(list)
        })

        builder.create().show()
    }

    private fun showListDetailActivity(list: TaskList) {
        val intent = Intent(this, ListDetailActivity::class.java)
        intent.putExtra(INTENT_LIST_KEY, list)
        //startActivity(intent)
        startActivityForResult(intent, LIST_DETAIL_REQUEST_CODE)
    }
}
