package e.ib.flighttracker1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import e.ib.flighttracker1.task.TaskRunner
import e.ib.flighttracker1.view.MyTableRow

class FlightsResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flights_result)

        val iata = intent.getCharSequenceExtra("iata")
        val tableLayout = findViewById<TableLayout>(R.id.t_layout_flights)

        val params = HashMap<String, String>()
        params["iataCode"] = iata?.toString() ?: ""
        params["type"] = "departure"
        val airport = TaskRunner.timetableForAirport(params)
        if (airport != null) {
            for (a in airport) {
                /*
                 *response z /timetables ma IATA lotniska departure i arrival, z którego pozyskuje AirportDAO
                 *response z /airports ma IATA miasta, z którego pozyskuje CityDAO
                 *response z /cityDatabase ma nazwę miasta ... mało praktyczne
                 *czasami IATA lotniska to też IATA miasta, ale nie jest to regułą
                 *bardzo obciążające aplikację
                 **/
                val map = HashMap<String, String>()
                map["codeIataAirport"] = a.arrival.iataCode ?: " "
                val airportArrival = TaskRunner.airportFromIata(map)
                map["codeIataAirport"] = a.departure.iataCode ?: " "
                val airportDeparture = TaskRunner.airportFromIata(map)
                map.clear()
                map["codeIataCity"] = airportDeparture?.codeIataCity ?: ""
                val airportDepartureCity = TaskRunner.cityFromIata(map)
                map["codeIataCity"] = airportArrival?.codeIataCity ?: ""
                val airportArrivalCity = TaskRunner.cityFromIata(map)

                val t1 = TextView(this.applicationContext)
                val t2 = TextView(this.applicationContext)
                val t3 = TextView(this.applicationContext)
                val t4 = TextView(this.applicationContext)

                val btn = Button(this.applicationContext)
                val row = MyTableRow(this.applicationContext)
                btn.setOnClickListener { onBtnClick(it) }
                btn.text = resources.getText(R.string.show_on_map)
                //zapisuje niezbędne pola obiektu do wykorzystania w następnym activity
                //brzydki zabieg, ale nie będzie trzeba wysyłać ponownie 3 requestów do api
                row.objs = arrayOf(
                    airportDeparture?.latitudeAirport.toString(),
                    airportDeparture?.longitudeAirport.toString(),
                    airportArrival?.latitudeAirport.toString(),
                    airportArrival?.longitudeAirport.toString(),
                    airportDepartureCity?.nameCity,
                    airportArrivalCity?.nameCity
                )

                row.addView(t1)
                row.addView(t2)
                row.addView(t3)
                row.addView(t4)
                row.addView(btn)

                t1.text = a.airline.name
                t2.text = airportArrivalCity?.nameCity
                t3.text = airportDepartureCity?.nameCity
                t4.text = a.departure.scheduledTime?.replace("T", " ")?.replace(":00.000", "")
                //workaround, DateTimeFormatter requires API 26, current is 19
                tableLayout.addView(row)
            }

            return
        }
        val tv = findViewById<TextView>(R.id.nearest_error)
        tableLayout.visibility = View.INVISIBLE
        tv.text = getString(R.string.error_no_flights)

    }

    private fun onBtnClick(view : View) {
        view as Button
        val row = view.parent as MyTableRow
        val objs = row.objs
        val intent = Intent(this, ShowFlightOnMapActivity::class.java).apply{
            putExtra("ad_lat", objs[0])
            putExtra("ad_lng", objs[1])
            putExtra("aa_lat", objs[2])
            putExtra("aa_lng", objs[3])
            putExtra("ad_name", objs[4])
            putExtra("aa_name", objs[5])
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

}
