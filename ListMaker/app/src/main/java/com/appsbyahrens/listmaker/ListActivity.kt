package com.appsbyahrens.listmaker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.FrameLayout

class ListActivity : AppCompatActivity(), ListSelectionFragment.OnFragmentInteractionListener {

    companion object {
        val INTENT_LIST_KEY = "IntentToListDetailActivity"
        val LIST_DETAIL_REQUEST_CODE = 666
    }

    lateinit var addListButton: FloatingActionButton

    private val TAG = ListActivity::class.java.simpleName
    private var listSelectionFragment = ListSelectionFragment.newInstance()
    private var fragmentContainer: FrameLayout? = null
    private var largeScreen = false
    private var listDetailFragment: ListDetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        Log.d(TAG, "onCreate called in ListActivity")

        listSelectionFragment = supportFragmentManager
                .findFragmentById(R.id.list_selection_fragment) as ListSelectionFragment
        fragmentContainer = findViewById(R.id.fragment_container)
        largeScreen = fragmentContainer != null

        if (largeScreen) {
            Log.d(TAG, "onCreate Has Large Screen!")
        } else {
            Log.d(TAG, "onCreate Has SMALLER Screen!")
        }

        addListButton = findViewById(R.id.add_list_button)
        addListButton.setOnClickListener { _ ->
            Log.d(TAG, "onClick showAddList")
            showAddListDialog()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                listSelectionFragment.save(it.getParcelableExtra(INTENT_LIST_KEY))
            }
        }
    }

    override fun onListItemClicked(list: TaskList) {
        showListDetailActivity(list)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        title = resources.getString(R.string.app_name)

        listDetailFragment?.taskList?.let {
            listSelectionFragment.listDataManager.save(it)
        }

        if (listDetailFragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .remove(listDetailFragment)
                    .commit()

            listDetailFragment = null
        }

        addListButton.setOnClickListener { _ ->
            Log.d(TAG, "onClick showAddList")
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
        builder.setPositiveButton(buttonTitle, { dialog, _ ->
            val listName = editText.text.toString()
            val list = TaskList(listName)

            listSelectionFragment.addTaskList(list)

            Log.d(TAG, "User entered a list name of $listName")
            dialog.dismiss()
            showListDetailActivity(list)
        })

        builder.create().show()
    }

    private fun showListDetailActivity(list: TaskList) {
        if (largeScreen == false) {
            Log.d(TAG, "showListDetail on small screen")
            val intent = Intent(this, ListDetailActivity::class.java)
            intent.putExtra(INTENT_LIST_KEY, list)
            //startActivity(intent)
            startActivityForResult(intent, LIST_DETAIL_REQUEST_CODE)
        } else {
            Log.d(TAG, "showListDetail on big screen")
            title = list.name
            listDetailFragment = ListDetailFragment.newInstance(list)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, listDetailFragment, getString(R.string.list_fragment_tag))
                    .addToBackStack(null)
                    .commit()

            addListButton.setOnClickListener { _ ->
                Log.d(TAG, "onClick showCreate")
                showCreateTaskDialog()
            }
        }
    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this)
                .setTitle(R.string.task_to_add)
                .setView(taskEditText)
                .setPositiveButton(R.string.add_task, { dialog, _ ->
                    val task = taskEditText.text.toString()
                    listDetailFragment?.addTask(task)
                    dialog.dismiss()
                })
                .create()
                .show()
    }
}
