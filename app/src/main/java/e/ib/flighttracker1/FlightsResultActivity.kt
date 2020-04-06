package e.ib.flighttracker1

import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import e.ib.flighttracker1.task.TaskRunner

class FlightsResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights_result)
        val iata = intent.getCharSequenceExtra("iata")
        val tableLayout = findViewById<TableLayout>(R.id.t_layout_flights)

        val params = HashMap<String, String>()
        params["iataCode"] = iata?.toString() ?: ""
        val airport = TaskRunner.timetableForAirport(params)
        if (airport != null) {
            for (a in airport) {
                val map = HashMap<String, String>()
                map["codeIataAirport"] = a.arrival.icaoCode ?: " "
                val airportArrival = TaskRunner.airportFromIata(map)

                map["codeIaraAirport"] = a.departure.icaoCode ?: " "
                val airportDeparture = TaskRunner.airportFromIata(map)

                val t1 = TextView(this.applicationContext)
                val t2 = TextView(this.applicationContext)
                val t3 = TextView(this.applicationContext)
                val t4 = TextView(this.applicationContext)
                val row = TableRow(this.applicationContext)


                row.addView(t1)
                row.addView(t2)
                row.addView(t3)
                row.addView(t4)

                t1.text = a.airline.name
                t2.text = airportArrival?.nameAirport
                t3.text = airportDeparture?.nameAirport
                t4.text = a.departure.scheduledTime
                tableLayout.addView(row)
            }

            return
        }
        val tv = findViewById<TextView>(R.id.nearest_error)
        tableLayout.visibility = View.INVISIBLE
        tv.text = getString(R.string.error_no_airports_nearby)

    }
}
