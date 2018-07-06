package com.appsbyahrens.placebook.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.appsbyahrens.placebook.R
import com.appsbyahrens.placebook.ui.PlaceInfo
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.Marker

class BookmarkInfoWindowAdapter(context: Activity) : GoogleMap.InfoWindowAdapter {

    private val contents: View = context.layoutInflater.inflate(R.layout.content_bookmark_info, null)

    override fun getInfoContents(marker: Marker?): View? {

        val titleView = contents.findViewById<TextView>(R.id.title)
        titleView.text = marker?.title ?: ""

        val phoneView = contents.findViewById<TextView>(R.id.phone)
        phoneView.text = marker?.snippet ?: ""

//        val bitmap = marker?.tag as? Bitmap
//        bitmap?.let {
//            Log.e("MapsActivity", "We have a Bitmap to display")
//            val imageView = contents.findViewById<ImageView>(R.id.bookmark_photo)
//            imageView.setImageBitmap(it)
//        }

        val placeInfo = marker?.tag as? PlaceInfo

        placeInfo?.image?.let {
            Log.e("MapsActivity", "We have a Bitmap to display")
            val imageView = contents.findViewById<ImageView>(R.id.bookmark_photo)
            imageView.setImageBitmap(it)
        }

        return contents
    }

    override fun getInfoWindow(marker: Marker?): View? {
        return null
    }
}