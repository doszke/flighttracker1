package e.ib.flighttracker1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class ShowFlightOnMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var adLat : String
    private lateinit var adLng : String
    private lateinit var aaLat : String
    private lateinit var aaLng : String
    private lateinit var adName : String
    private lateinit var aaName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_flight_on_map)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        adLat = intent.getStringExtra("ad_lat")
        adLng = intent.getStringExtra("ad_lng")
        aaLat = intent.getStringExtra("aa_lat")
        aaLng = intent.getStringExtra("aa_lng")
        adName = intent.getStringExtra("ad_name")
        aaName = intent.getStringExtra("aa_name")

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val adMarker = LatLng(adLat.toDouble(), adLng.toDouble())
        val aaMarker = LatLng(aaLat.toDouble(), aaLng.toDouble())
        Log.d("markers", "aa: $aaLat $aaLng $aaName, ad: $adLat $adLng $adName")
        mMap.addMarker(MarkerOptions().position(adMarker).title(adName))
        mMap.addMarker(MarkerOptions().position(aaMarker).title(aaName))
        mMap.addPolyline(PolylineOptions().add(aaMarker).add(adMarker))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(adMarker))
    }
}
