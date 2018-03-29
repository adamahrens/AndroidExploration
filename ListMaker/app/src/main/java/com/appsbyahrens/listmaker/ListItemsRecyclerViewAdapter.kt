package com.appsbyahrens.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListItemsRecyclerViewAdapter(var taskList: TaskList) : RecyclerView.Adapter<ListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.task_view_holder, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.tasks.size
    }

    override fun onBindViewHolder(holder: ListItemViewHolder?, position: Int) {
        if (holder != null) {
            val item = taskList.tasks[position]
            holder.taskPosition?.text = (position + 1).toString()
            holder.taskTitle?.text = item
        }
    }
}