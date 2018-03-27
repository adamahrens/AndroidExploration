package com.appsbyahrens.listmaker

import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity

class ListDataManager(val context: AppCompatActivity) {

    fun save(list: TaskList) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()
        sharedPreferences.putStringSet(list.name, list.tasks.toHashSet())
        sharedPreferences.apply()
    }

    fun getTaskLists(): ArrayList<TaskList> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val contents = sharedPreferences.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in contents) {
            val hashSet = taskList.value as HashSet<String>
            val list = TaskList(taskList.key, ArrayList(hashSet))
            taskLists.add(list)
        }

        return taskLists
    }
}