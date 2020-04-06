package e.ib.flighttracker1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NearestAirportActivity : AppCompatActivity() {

    private lateinit var distanceEt : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearest_airport)
        distanceEt = findViewById(R.id.distance_et)
    }

    fun searchAirport(view : View) {
        val choice = this.intent.getSerializableExtra("userChoice")
        val intent = when (choice) {
            (UserChoice.NEAREST_AIRPORT_LIST) -> Intent(this, NearbyAirportResult::class.java)
            (UserChoice.NEAREST_AIRPORT_MAP) -> Intent(this, NearbyAirportsMapsActivity::class.java)
            else -> Intent(this, MainActivity::class.java)
        }
        intent.apply {
            putExtra("distance", distanceEt.text)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

}