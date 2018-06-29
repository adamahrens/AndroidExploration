package com.appsbyahrens.placebook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import com.appsbyahrens.placebook.adapter.BookmarkInfoWindowAdapter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.PlacePhotoMetadata
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    companion object {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsActivity"
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var client: FusedLocationProviderClient
    private lateinit var googleClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupLocationClient()
        setupGoogleClient()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // Try Finding The Person
        getCurrentLocation()

        // Clicks on Points of Interest
        //googleMap.setOnPoiClickListener { Toast.makeText(this, it.name, Toast.LENGTH_LONG).show() }
        googleMap.setOnPoiClickListener { displayPointOfInterest(it) }

        googleMap.setInfoWindowAdapter(BookmarkInfoWindowAdapter(this))
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e(TAG, "Google connection to Play Services failed: " + connectionResult.errorMessage)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION &&
                grantResults.size == 1 &&
                grantResults.first() == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "Permission Granted. Getting Current Location")
            getCurrentLocation()
        } else {
            Log.e(TAG, "Permission Denied")
        }
    }

    private fun setupGoogleClient() {
        googleClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Places.GEO_DATA_API)
                .build()
    }

    private fun displayPointOfInterest(poi: PointOfInterest) {
        displayPointOfInterestName(poi)
    }

    private fun displayMarker(place: Place, bitmap: Bitmap?) {
        Log.e(TAG, "Display marker for ${ place.name }")
        val marker = googleMap.addMarker(MarkerOptions()
                .position(place.latLng)
                .title(place.name as String?)
                .snippet(place.phoneNumber as String?))
        marker?.tag = bitmap
    }

    private fun displayPhoto(place: Place, photo: PlacePhotoMetadata) {
        val width = resources.getDimensionPixelSize(R.dimen.default_image_width)
        val height = resources.getDimensionPixelSize(R.dimen.default_image_height)
        photo.getScaledPhoto(googleClient, width, height).setResultCallback {
            if (it.status.isSuccess) {
                val image = it.bitmap
                displayMarker(place, image)
            } else {
                Log.e(TAG, "Error getting scaled Photo")
            }
        }
    }

    private fun displayPhotoOfPlace(place: Place) {
        Places.GeoDataApi.getPlacePhotos(googleClient, place.id).setResultCallback {
            if (it.status.isSuccess) {
                val buffer = it.photoMetadata
                if (buffer.count > 0) {
                    val photo = buffer.get(0).freeze()
                    displayPhoto(place, photo)
                }

                buffer.release()
            } else {
                Log.e(TAG, "Error getting Photo for Point of Interest")
            }
        }
    }

    private fun displayPointOfInterestName(poi: PointOfInterest) {
        Places.GeoDataApi.getPlaceById(googleClient, poi.placeId).setResultCallback {
            if (it.status.isSuccess && it.count > 0) {
                val place = it.get(0).freeze()
                //Toast.makeText(this, "${place.name} ${place.phoneNumber}", Toast.LENGTH_LONG)
                //        .show()
                displayPhotoOfPlace(place)
            } else {
                Log.e(TAG, "Error getting Data for Point of Interest")
            }

            it.release()
        }
    }

    private fun setupLocationClient() {
        client = LocationServices.getFusedLocationProviderClient(this)
        Log.e(TAG, "Setting up LocationServices Client")
    }

    private fun requestLocationAccess() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_LOCATION)
        Log.e(TAG, "Requesting Access to Location Services")
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationAccess()
            Log.e(TAG, "Location Services Not Granted Permission")
        } else {

            // Add blue dot for your location
            googleMap.isMyLocationEnabled = true

            // Already authorized to use location services
            client.lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    val latLong = LatLng(it.result.latitude, it.result.longitude)
                    val zoomTo = CameraUpdateFactory.newLatLngZoom(latLong, 16.0f)
                    googleMap.moveCamera(zoomTo)
                    Log.e(TAG, "Found your location. Zooming in....")
                } else {
                    Log.e(TAG, "No Location Found!")
                }
            }
        }
    }
}
