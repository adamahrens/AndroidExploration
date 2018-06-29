package com.appsbyahrens.placebook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import com.google.android.gms.location.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsActivity"
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var client: FusedLocationProviderClient
    // Not needed since property on googleMap
    //private var locationRequest: LocationRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupLocationClient()
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

        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        this.googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        // Try Finding The Person
        getCurrentLocation()
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
            /*
             *
             *  No longer needed since setting property on googleMap
            if (locationRequest == null) {
                locationRequest = LocationRequest.create()
                locationRequest?.let { request ->
                    request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                    request.interval = 5000
                    request.fastestInterval = 1000

                    val callback = object: LocationCallback() {
                        override fun onLocationResult(p0: LocationResult?) {
                            super.onLocationResult(p0)
                            Log.e(TAG, "Location has changed!!!")
                            getCurrentLocation()
                        }
                    }

                    client.requestLocationUpdates(locationRequest, callback, null)
                }
            }
            */

            // Add blue dot for your location
            googleMap.isMyLocationEnabled = true

            // Already authorized to use location services
            client.lastLocation.addOnCompleteListener {
                if (it.result != null) {
                    //googleMap.clear()

                    val latLong = LatLng(it.result.latitude, it.result.longitude)
                    //googleMap.addMarker(MarkerOptions().position(latLong).title("Found You!"))

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
