package com.appsbyahrens.listmaker

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class ListSelectionFragment : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    lateinit var listDataManager: ListDataManager
    lateinit var listsRecyclerView: RecyclerView
    private var listener: OnFragmentInteractionListener? = null
    private val TAG = ListActivity::class.java.simpleName

    fun addTaskList(list: TaskList) {
        Log.d(TAG, "Addings List from Fragment")
        listDataManager.save(list)
        val adapter = listsRecyclerView.adapter as ListSelectionRecyclerViewAdapter
        adapter.addTaskList(list)
    }

    fun save(list: TaskList) {
        listDataManager.save(list)
        updateLists()
    }

    private fun updateLists() {
        val lists = listDataManager.getTaskLists()
        listsRecyclerView.adapter =
                ListSelectionRecyclerViewAdapter(lists, this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
            listDataManager = ListDataManager(context)
            Log.d(TAG, "Creating ListDataManager in onAttach")
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val lists = listDataManager.getTaskLists()

        view?.let {
            Log.d(TAG, "View created in onActivityCreated")
            listsRecyclerView = it.findViewById<RecyclerView>(R.id.lists_recycler_view)
            listsRecyclerView.layoutManager = LinearLayoutManager(activity)
            listsRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists, this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_selection, container, false)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun listItemClicked(list: TaskList) {
        Log.d(TAG, "listItemClicked in Fragment. Calling listener")
        listener?.onListItemClicked(list)
    }

    interface OnFragmentInteractionListener {
        fun onListItemClicked(list: TaskList)
    }

    companion object {
        fun newInstance(): ListSelectionFragment {
            val fragment = ListSelectionFragment()
            return fragment
        }
    }
}
