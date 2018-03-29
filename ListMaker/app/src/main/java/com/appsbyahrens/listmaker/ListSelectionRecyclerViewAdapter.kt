package com.appsbyahrens.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListSelectionRecyclerViewAdapter(val taskLists: ArrayList<TaskList>, val clickListener: ListSelectionRecyclerViewClickListener): RecyclerView.Adapter<ListSelectionViewHolder>() {

    interface ListSelectionRecyclerViewClickListener {
        fun listItemClicked(list: TaskList)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSelectionViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_selection_view_holder, parent,false)
        return ListSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskLists.size
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder?, position: Int) {
        if (holder != null) {
            val item = taskLists[position]
            holder.listTitle?.text = item.name
            holder.listPosition?.text = (position + 1).toString()
            holder.itemView.setOnClickListener { _ ->
                clickListener.listItemClicked(item)
            }
        }
    }

    fun addTaskList(list: TaskList) {
        taskLists.add(list)

        notifyDataSetChanged()
    }
}