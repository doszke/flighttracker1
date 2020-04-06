package e.ib.flighttracker1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import e.ib.flighttracker1.task.TaskRunner

class NearbyAirportResult : AppCompatActivity() {

    private val ex = Exception("Location disabled. ")

    private lateinit var tableLayout : TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearest_airport_result)
        tableLayout = findViewById(R.id.t_layout)
        val distance = intent.getCharSequenceExtra("distance")
        val location = getLocation()

        if (location != null) {
            val params = HashMap<String, String>()
            params["lat"] = location.latitude.toString()
            params["lng"] = location.longitude.toString()
            params["distance"] = distance?.toString() ?: "100"
            val airport = TaskRunner.nearbyAirports(params)
            if (airport != null) {
                for (a in airport) {
                    val isRailwayStation = a.nameAirport.toLowerCase().contains("rail")
                    val isBusStation = a.nameAirport.toLowerCase().contains("bus")
                    if (isRailwayStation || isBusStation) continue //nie wiem dlaczego API lotnicze ma stacje autobusowe i dworce kolejowe, ale ok

                    val t1 = TextView(this.applicationContext)
                    val t2 = TextView(this.applicationContext)
                    val t3 = TextView(this.applicationContext)
                    val t4 = TextView(this.applicationContext)
                    val btn = Button(this.applicationContext)
                    val row = TableRow(this.applicationContext)

                    btn.setOnClickListener { onBtnClick(it) }
                    btn.text = getString(R.string.btn_nearby_txt)

                    row.addView(t1)
                    row.addView(t2)
                    row.addView(t3)
                    row.addView(t4)
                    row.addView(btn)

                    t1.text = a.codeIcaoAirport
                    t2.text = a.nameAirport
                    t3.text = a.nameCountry
                    t4.text = a.distance.toString()
                    tableLayout.addView(row)
                }

                return
            }
            val tv = findViewById<TextView>(R.id.nearest_error)
            tableLayout.visibility = View.INVISIBLE
            tv.text = getString(R.string.error_no_airports_nearby)
        }
        MainActivity.throwableHandler.handle(ex)
    }

    private fun onBtnClick(view : View) {
        val tr = view.parent as TableRow
        val iata = (tr.getChildAt(0) as TextView).text
        Log.d("IATA", iata.toString())
        val intent = Intent(this.applicationContext, FlightsResultActivity::class.java). apply {
            putExtra("iata", iata)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
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
