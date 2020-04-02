package e.ib.flighttracker1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import e.ib.flighttracker1.model.FlightDAO
import e.ib.flighttracker1.model.OpenSkyRestAPIUrl

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv: TextView = findViewById(R.id.tv)

        OpenSkyRestAPIUrl.init(applicationContext)
        var otp : Array<FlightDAO> = arrayOf()
        try {
            var task = FlightsAllTask()
            otp = task.execute(
                (System.currentTimeMillis() / 1000L - 3600 * 24 * 12 * 4 - 3600).toString(),
                (System.currentTimeMillis() / 1000L - 3600 * 24 * 12 * 4).toString()
            ).get()
            Log.d("otp", otp.size.toString())
            otp.forEach { Log.d("TESTTTTTTTTTT", it.icao24) }
        } finally {
            for (o in otp) {
                var task2 = StatesAllTask()
                val otp2 =
                    task2.execute((System.currentTimeMillis() / 1000L - 3600 * 24 * 12 * 4).toString(), o.icao24)
                        .get()
                if (otp2.stateVector != null) {
                    tv.text = otp2.stateVector.toString() + otp2.toString()
                    Log.d("statevectordao", otp2.stateVector.toString())
                    break
                }
            }
        }
    }
}
