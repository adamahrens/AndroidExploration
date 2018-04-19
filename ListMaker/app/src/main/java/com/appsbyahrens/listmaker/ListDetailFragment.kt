package com.appsbyahrens.listmaker

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ListDetailFragment : Fragment() {
    lateinit var taskList: TaskList
    private lateinit var listItemsRecyclerView : RecyclerView

    fun addTask(task: String) {
        taskList.tasks.add(task)
        val listRecyclerAdapter =  listItemsRecyclerView.adapter as
                ListItemsRecyclerViewAdapter
        listRecyclerAdapter.taskList = taskList
        listRecyclerAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        taskList = arguments.getParcelable(ListActivity.INTENT_LIST_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_detail, container, false)

        view?.let {
            listItemsRecyclerView = it.findViewById<RecyclerView>(R.id.list_items_recycler_view)
            listItemsRecyclerView.adapter = ListItemsRecyclerViewAdapter(taskList)
            listItemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        }
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(taskList: TaskList): ListDetailFragment {
            val fragment = ListDetailFragment()
            val args = Bundle()
            args.putParcelable(ListActivity.INTENT_LIST_KEY, taskList)
            fragment.arguments = args
            return fragment
        }
    }
}
