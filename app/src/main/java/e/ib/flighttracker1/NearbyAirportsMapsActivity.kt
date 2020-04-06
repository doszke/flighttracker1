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
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import e.ib.flighttracker1.task.TaskRunner

class NearbyAirportsMapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var distance : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        distance = intent.getCharSequenceExtra("distance").toString()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val location = getLocation()
        if (location != null) {
            val params = HashMap<String, String>()
            params["lat"] = location.latitude.toString()
            params["lng"] = location.longitude.toString()
            params["distance"] = distance
            val airport = TaskRunner.nearbyAirports(params)
            if (airport != null) {
                for (a in airport) {
                    if (a.nameAirport.toLowerCase().contains("rail") || a.nameAirport.toLowerCase().contains("bus")) continue
                    val marker = LatLng(a.latitudeAirport.toDouble(), a.longitudeAirport.toDouble())
                    mMap!!.addMarker(MarkerOptions().position(marker).title(a.codeIataAirport + " " + a.nameAirport + ", " + a.nameCountry))
                    mMap!!.addCircle(CircleOptions().center(LatLng(location.latitude, location.longitude)).radius(distance.toDouble()*1000))
                    mMap!!.moveCamera(CameraUpdateFactory.newLatLng(marker))
                }
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