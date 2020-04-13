package e.ib.flighttracker1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import e.ib.flighttracker1.task.TaskRunner

class GoogleMapsActivity : FragmentActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null

    private lateinit var mode : GoogleMapsMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        mode = intent.getSerializableExtra("mode") as GoogleMapsMode
    }

    override fun onMapReady(googleMap: GoogleMap) {
        when (mode) {
            GoogleMapsMode.ALL_FLIGHTS_IN_RADIUS -> allFlightsInRadius(googleMap)
            GoogleMapsMode.ALL_AIRPORTS_IN_RADIUS -> nearestAirports(googleMap)
            GoogleMapsMode.ONE_FLIGHT -> oneFlight(googleMap)
        }
    }


    private fun getLocation(): Location? {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val providers = lm.getProviders(true)
        var location: Location? = null
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            for (provider in providers) {
                val l = lm.getLastKnownLocation(provider)
                if (l != null) {
                    return l
                }
            }
        }
        return null
    }

    private fun allFlightsInRadius(googleMap: GoogleMap) {
        mMap = googleMap

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
                        .rotation(a.geography.direction)
                        .flat(true)
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

    private fun oneFlight(googleMap: GoogleMap) {
        mMap = googleMap

        //te parametry są podawane w intencie tylko dla tej funkcjonalności, więc tu pobieram
        val a_iata = intent.getStringExtra("a_iata")
        val d_iata = intent.getStringExtra("d_iata")

        val params = HashMap<String, String>()
        params["codeIataAirport"] = a_iata
        val arrival = TaskRunner.airportFromIata(params)

        params.clear()
        params["codeIataAirport"] = d_iata
        val departure = TaskRunner.airportFromIata(params)
        if (departure != null && arrival != null) {
            val adMarker = LatLng(departure.latitudeAirport.toDouble(), departure.longitudeAirport.toDouble())
            val aaMarker = LatLng(arrival.latitudeAirport.toDouble(), arrival.longitudeAirport.toDouble())
            mMap!!.addMarker(MarkerOptions().position(adMarker).title(departure.nameAirport))
            mMap!!.addMarker(MarkerOptions().position(aaMarker).title(arrival.nameAirport))
            mMap!!.addPolyline(PolylineOptions().add(aaMarker).add(adMarker))
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(adMarker))
        }
    }


    private fun nearestAirports(googleMap: GoogleMap) {
        mMap = googleMap
        val location = getLocation()
        if (location != null) {
            //ten parametr jest podawany w intencie tylko dla tej funkcjonalności, więc tu pobieram
            val distance = intent.getCharSequenceExtra("distance").toString()
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


}