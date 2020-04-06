package e.ib.flighttracker1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import e.ib.flighttracker1.task.TaskRunner

class FlightsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val template = "%s-%s"
        val location = getLocation()
        if (location != null) {
            val params = HashMap<String, String>()
            params["lat"] = location.latitude.toString()
            params["lng"] = location.longitude.toString()
            params["distance"] = "2000"
            val flights = TaskRunner.flightsInRange(params)
            if (flights != null) {
                for (a in flights) {
                    val marker = LatLng(a.geography.latitude.toDouble(), a.geography.longitude.toDouble())
                    mMap!!.addMarker(MarkerOptions()
                        .position(marker)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.aircraft))
                        .title(template.format(a.departure.iataCode, a.arrival.iataCode)
                        )
                    )
                }
                mMap!!.addCircle(CircleOptions().center(LatLng(location.latitude, location.longitude)).radius(2000000.toDouble()))
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
            }
        }
    }


    private fun getLocation() : Location? {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = lm.getProviders(true)
        var location: Location? = null
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            for (provider in providers) {
                val l = lm.getLastKnownLocation(provider)
                if (l != null) {
                    return l
                }
            }
        }
        return null
    }


}