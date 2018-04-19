package com.appsbyahrens.listmaker

import android.content.Context
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity

class ListDataManager(val context: Context) {

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
            val hashSet = taskList.value as? HashSet<String>
            hashSet.let { set ->
                val list = TaskList(taskList.key, ArrayList(set))
                taskLists.add(list)
            }
        }

        return taskLists
    }
}