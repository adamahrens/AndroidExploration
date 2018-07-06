package com.appsbyahrens.placebook.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.appsbyahrens.placebook.model.Bookmark

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM Bookmark")
    fun all(): LiveData<List<Bookmark>>

    @Delete
    fun delete(bookmark: Bookmark)

    @Update(onConflict = REPLACE)
    fun update(bookmark: Bookmark)

    @Insert(onConflict = IGNORE)
    fun insert(bookmark: Bookmark) : Long

    @Query("SELECT * FROM Bookmark WHERE id = :id")
    fun loadBookmark(id: Long): Bookmark

    @Query("SELECT * FROM Bookmark WHERE id = :id")
    fun loadLiveBookmark(id: Long): LiveData<Bookmark>
}