package hu.ait.mapdemo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import hu.ait.mapdemo.databinding.ActivityMapsBinding
import java.util.*
import kotlin.concurrent.thread

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    MyLocationManager.OnNewLocationAvailable {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var myLocationManager: MyLocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myLocationManager = MyLocationManager(this,
            this)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        requestNeededPermission()
    }

    fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
        } else {
            // we have the permission
            myLocationManager.startLocationMonitoring()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,
                        "ACCESS_FINE_LOCATION perm granted", Toast.LENGTH_SHORT)
                        .show()

                    myLocationManager.startLocationMonitoring()
                } else {
                    Toast.makeText(
                        this,
                        "ACCESS_FINE_LOCATION perm NOT granted", Toast.LENGTH_SHORT
                    ).show()
                }
                return
            }
        }
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
        mMap = googleMap

        mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this, R.raw.mapstyle)
        )

        binding.toggleMapMode.setOnClickListener {
            if (binding.toggleMapMode.isChecked) {
                mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            } else {
                mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            }
        }
        mMap.isTrafficEnabled = true


        val hungary = LatLng(47.0, 19.0)
        mMap.addMarker(
            MarkerOptions().position(hungary).title(
                "Marker in Hungary"
            )
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hungary))


        mMap.setOnMapClickListener {
            val newMarker = mMap.addMarker(
                MarkerOptions().position(it)
                    .title("Demo marker")
                    .snippet(
                        """
                        Location: ${it.latitude}, ${it.longitude}
                    """.trimIndent()
                    )
            )
            newMarker?.isDraggable = true

            val random = Random(System.currentTimeMillis())
            val cameraPostion = CameraPosition.Builder()
                .target(it)
                .zoom(5f + random.nextInt(15))
                .tilt(30f + random.nextInt(15))
                .bearing(-45f + random.nextInt(90))
                .build()
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPostion))
            //mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
        }

        mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker): Boolean {
//                Toast.makeText(
//                    this@MapsActivity,
//                    "${marker.position.latitude}," +
//                            " ${marker.position.longitude}",
//                    Toast.LENGTH_LONG
//                ).show()

                geocodeLocation(LatLng(
                    marker.position.latitude,
                    marker.position.longitude
                ))

                return true
            }
        })

        val polyRect: PolygonOptions = PolygonOptions().add(
            LatLng(44.0, 19.0),
            LatLng(44.0, 26.0),
            LatLng(48.0, 26.0),
            LatLng(48.0, 19.0))
        val poly = mMap.addPolygon(polyRect)
        poly.fillColor = Color.argb(
            100, 0,255,0
        )
    }

    override fun onNewLocation(location: Location) {
        // print location data on a textview..
        binding.tvData.text =
            """
                Lat: ${location.latitude}
                Lng: ${location.longitude}
                Alt: ${location.altitude}
                Speed: ${location.speed}
                Accuracy: ${location.accuracy}
            """.trimIndent()


        mMap.addMarker(
            MarkerOptions().position(
                LatLng(location.latitude,location.longitude)
            ).title("I was here"))
    }



    private fun geocodeLocation(location: LatLng) {
        thread {
            try {
                val gc = Geocoder(this, Locale.getDefault())
                var addrs: List<Address> =
                    gc.getFromLocation(location.latitude,
                        location.longitude, 3)
                val addr =
                    "${addrs[0].getAddressLine(0)}, ${addrs[0].getAddressLine(1)}, ${addrs[0].getAddressLine(2)}"

                runOnUiThread {
                    Toast.makeText(this, addr, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MapsActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


}