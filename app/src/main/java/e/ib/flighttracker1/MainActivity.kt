package e.ib.flighttracker1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import e.ib.flighttracker1.task.model.AviationEdgeUriFactory
import e.ib.flighttracker1.task.TaskFacade
import e.ib.flighttracker1.task.async.AirportFromIataTask
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        val throwableHandler = ThrowableHandler.getInstance()
    }

    class ThrowableHandler private constructor() {

        companion object {
            private var handler = ThrowableHandler()

            fun getInstance() : ThrowableHandler {
                return handler
            }

        }

        internal lateinit var applicationContext : Context
        fun handle(t : Throwable) {
            Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AviationEdgeUriFactory.init(applicationContext)
        throwableHandler.applicationContext = applicationContext


        val db = Firebase.firestore

        //location
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 100
            )
        }
        val providers = lm.getProviders(true)
        var location: Location? = null
        for (provider in providers) {
            var l = lm.getLastKnownLocation(provider)
            if (l != null) {
                location = l
                break
            }
        }
        Log.d("location", (location == null).toString())
        var longitude: Double = 0.0
        var latitude: Double = 0.0
        if (location != null) {
            longitude = location.longitude
            latitude = location.latitude
        }

        val map = HashMap<String, String>()
        map["lng"] = longitude.toString()
        map["lat"] = latitude.toString()
        map["distance"] = "60"
        //map["city"] = "wroc"

        val s = TaskFacade.nearbyAirports(map)
        if (s != null) {
            for (o in s) {
                Log.d("output", o.toString())
            }
        }

        map.clear()
        map["city"] = "wroc"

        val a = TaskFacade.airportsByCity(map)
        if (a != null) {
            for (o in a.airportsByCities) {
                Log.d("output", o.toString())
            }
        }

        map.clear()
        map["iataCode"] = "WRO"
        map["type"] = "departure"
        map["status"] = "active"

        val c = TaskFacade.timetableForAirport(map)
        if (c != null) {
            for (i in c) {
                Log.d("output", i.toString())
            }
        }

        map.clear()
        map["codeIataAirport"] = "WRO"
        val obj = TaskFacade.airportFromIata(map)
        if (obj != null) {
            Log.d("outputnew", obj.toString())
        }
    }
}
