package e.ib.flighttracker1

import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import e.ib.flighttracker1.task.TaskRunner
import e.ib.flighttracker1.view.MyTableRow
import android.view.ViewGroup





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
                 * Aby uzyskać nazwy miast departure i arrival z aviation-edge należy:
                 * pobrać response z /timetables ma IATA lotniska departure i arrival, z którego pozyskuje AirportDAO
                 * pobrać response z /airports ma IATA miasta, z którego pozyskuje CityDAO
                 * pobrać response z /cityDatabase ma nazwę miasta ... mało praktyczne
                 * czasami IATA lotniska to też IATA miasta, ale nie jest to regułą
                 * bardzo obciążające aplikację - pozostaję przy kodach
                 * mało user firendly, ale aplikacja nie wiesza się przy 5 row'ach
                 **/

                var row = MyTableRow(this.applicationContext)

                tableLayout.addView(row)


                val t1 = TextView(applicationContext)
                val t2 = TextView(applicationContext)
                val t3 = TextView(applicationContext)
                val t4 = TextView(applicationContext)

                val btn = Button(applicationContext)
                btn.setOnClickListener { onBtnClick(it) }
                btn.text = resources.getText(R.string.show_on_map)
                //zapisuje niezbędne pola obiektu do wykorzystania w następnym activity
                //brzydki zabieg, ale nie będzie trzeba wysyłać ponownie 3 requestów do api
                row.objs = arrayOf(
                    a.arrival.iataCode,
                    a.departure.iataCode
                )

                row.addView(t1)
                row.addView(t2)
                row.addView(t3)
                row.addView(t4)
                row.addView(btn)


                t1.text = a.airline.name
                t2.text = a.departure.iataCode
                t3.text = a.arrival.iataCode
                t4.text = a.departure.scheduledTime?.replace("T", " ")?.replace(":00.000", "")
                //workaround, DateTimeFormatter requires API 26, current is 19
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
        val intent = Intent(this, GoogleMapsActivity::class.java).apply{
            putExtra("a_iata", objs[0])
            putExtra("d_iata", objs[1])
            putExtra("mode", GoogleMapsMode.ONE_FLIGHT)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

}
