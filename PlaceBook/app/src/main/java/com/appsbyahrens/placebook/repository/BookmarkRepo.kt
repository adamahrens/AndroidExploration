package com.appsbyahrens.placebook.repository

import android.arch.lifecycle.LiveData
import android.content.Context
import com.appsbyahrens.placebook.db.BookmarkDao
import com.appsbyahrens.placebook.db.PlaceBookDatabase
import com.appsbyahrens.placebook.model.Bookmark

class BookmarkRepo(private val context: Context) {
    private var database: PlaceBookDatabase = PlaceBookDatabase.getInstance(context)
    private var dao: BookmarkDao = database.dao()

    fun addBookmark(bookmark: Bookmark): Long? {
        val newId = dao.insert(bookmark)
        bookmark.id = newId
        return newId
    }

    fun createBookmark(): Bookmark {
        return Bookmark()
    }

    val bookmarks: LiveData<List<Bookmark>>
    get() { return dao.all() }
}