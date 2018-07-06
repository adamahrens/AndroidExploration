package com.appsbyahrens.placebook.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.graphics.Bitmap
import android.util.Log
import com.appsbyahrens.placebook.model.Bookmark
import com.appsbyahrens.placebook.repository.BookmarkRepo
import com.google.android.gms.location.places.Place
import com.google.android.gms.maps.model.LatLng

class MapsViewModel(application: Application): AndroidViewModel(application) {

    data class BookmarkMarkerView(var id: Long? = null, var location: LatLng = LatLng(0.0, 0.0))

    private val TAG = "MapsViewModel"

    private var repository = BookmarkRepo(getApplication())
    private var bookmarks: LiveData<List<BookmarkMarkerView>>? = null

    fun addBookmarkFromPlace(place: Place, image: Bitmap) {
        val bookmark = repository.createBookmark()
        bookmark.placeId = place.id
        bookmark.name = place.name.toString()
        bookmark.longitude = place.latLng.longitude
        bookmark.latitude = place.latLng.latitude
        bookmark.phone = place.phoneNumber.toString()
        bookmark.address = place.address.toString()

        val savedBookMarkId = repository.addBookmark(bookmark)
        Log.i(TAG, "New Bookmark $savedBookMarkId - ${bookmark.name} added to database")
    }

    fun getBookmarkViews(): LiveData<List<BookmarkMarkerView>>? {
        if (bookmarks == null) {
            convertBookmarksToMarkerView()
        }

        return bookmarks
    }

    private fun convertBookmarkToBookmarkMarkerView(bookmark: Bookmark): BookmarkMarkerView {
        return BookmarkMarkerView(bookmark.id, LatLng(bookmark.latitude, bookmark.longitude))
    }

    private fun convertBookmarksToMarkerView() {
        val all = repository.bookmarks
        bookmarks = Transformations.map(all) { marks ->
            val views = marks.map { convertBookmarkToBookmarkMarkerView(it) }
            views
        }
    }
}