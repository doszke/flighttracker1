package e.ib.flighttracker1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoggedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged)
    }

    fun nearestAirport(view : View) {
        val intent = Intent(this, NearestAirportActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .putExtra("userChoice", UserChoice.NEAREST_AIRPORT_MAP)
        startActivity(intent)
    }

    fun nearestAirportList(view : View) {
        val intent = Intent(this, NearestAirportActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .putExtra("userChoice", UserChoice.NEAREST_AIRPORT_LIST)
        startActivity(intent)
    }

    fun flights(view : View) {
        val intent = Intent(this, FlightsActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    fun airportByCity(view : View) {
        val intent = Intent(this, AirportsByCityActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

}
