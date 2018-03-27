package com.appsbyahrens.listmaker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ListSelectionRecyclerViewAdapter: RecyclerView.Adapter<ListSelectionViewHolder>() {

    val items = arrayOf("Shopping List", "Chores", "Pet Duties", "Work Assignments")

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSelectionViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_selection_view_holder, parent,false)
        return ListSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder?, position: Int) {
        if (holder != null) {
            val item = items[position]
            holder.listTitle?.text = item
            holder.listPosition?.text = (position + 1).toString()
        }
    }
}