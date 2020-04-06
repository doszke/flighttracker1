package e.ib.flighttracker1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import e.ib.flighttracker1.task.TaskRunner

class AirportsByCityActivity : AppCompatActivity() {

    private lateinit var city_t_layout: TableLayout
    private lateinit var city_et :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_airports_by_city)
        city_et = findViewById(R.id.city_et)
        city_t_layout = findViewById(R.id.airports_t_layout)
    }

    fun onBtnClickListener(view : View) {
        val city = city_et.text
        val params = HashMap<String, String>()
        params["city"] = city.toString()
        val airports = TaskRunner.airportsByCity(params)
        if (airports != null) {
            if (airports.airportsByCities != null) { //nie jest zawsze prawdziwe
                val header = findViewById<TableRow>(R.id.header)
                city_t_layout.removeAllViews()
                city_t_layout.addView(header)
                for (a in airports.airportsByCities) {
                    val isRailwayStation = a.nameAirport.toLowerCase().contains("rail")
                    val isBusStation = a.nameAirport.toLowerCase().contains("bus")
                    if (isRailwayStation || isBusStation) continue //nie wiem dlaczego API lotnicze ma stacje autobusowe i dworce kolejowe, ale ok

                    val t1 = TextView(this.applicationContext)
                    val t2 = TextView(this.applicationContext)
                    val t3 = TextView(this.applicationContext)
                    val btn = Button(this.applicationContext)
                    val row = TableRow(this.applicationContext)

                    btn.setOnClickListener { onBtnClick(it) }
                    btn.text = getString(R.string.btn_nearby_txt)

                    row.addView(t1)
                    row.addView(t2)
                    row.addView(t3)
                    row.addView(btn)

                    t1.text = a.codeIataAirport
                    t2.text = a.nameAirport
                    t3.text = a.nameCountry
                    city_t_layout.addView(row)
                }
            }
        }
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
}
