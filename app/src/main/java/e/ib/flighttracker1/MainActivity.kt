package e.ib.flighttracker1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import e.ib.flighttracker1.model.FlightDAO
import e.ib.flighttracker1.model.OpenSkyRestAPIUrl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        OpenSkyRestAPIUrl.init(applicationContext)

        val task = FlightsAllTask()
        val output = task.execute((System.currentTimeMillis() / 1000L - 3600*24*30*12).toString(),
            System.currentTimeMillis().toString()
        ).get()
        when (output) {
            is Throwable -> Toast.makeText(applicationContext, output.message, Toast.LENGTH_LONG).show()
            is Array<*> -> for (o in output) {
                if (o is FlightDAO) {
                    Toast.makeText(applicationContext, o.icao24, Toast.LENGTH_LONG).show()
                }
            }
            else -> Toast.makeText(applicationContext, "nothing", Toast.LENGTH_LONG).show()
        }

    }
}
