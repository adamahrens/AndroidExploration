package com.appsbyahrens.listmaker

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    val taskPosition = itemView?.findViewById<TextView>(R.id.taskNumber)
    val taskTitle = itemView?.findViewById<TextView>(R.id.taskString)
}