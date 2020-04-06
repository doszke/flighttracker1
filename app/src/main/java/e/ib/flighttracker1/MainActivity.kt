package e.ib.flighttracker1

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import e.ib.flighttracker1.task.TaskRunner
import e.ib.flighttracker1.task.model.AviationEdgeUriFactory
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEt : EditText
    private lateinit var passwordEt : EditText

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
        auth = FirebaseAuth.getInstance()
        emailEt = findViewById(R.id.username)
        passwordEt = findViewById(R.id.password)

        //location

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
        /*
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

        val s = TaskRunner.nearbyAirports(map)
        if (s != null) {
            for (o in s) {
                Log.d("output", o.toString())
            }
        }

        map.clear()
        map["city"] = "wroc"

        val a = TaskRunner.airportsByCity(map)
        if (a != null) {
            for (o in a.airportsByCities) {
                Log.d("output", o.toString())
            }
        }

        map.clear()
        map["iataCode"] = "WRO"
        map["type"] = "departure"
        map["status"] = "active"

        val c = TaskRunner.timetableForAirport(map)
        if (c != null) {
            for (i in c) {
                Log.d("output", i.toString())
            }
        }

        map.clear()
        map["codeIataAirport"] = "WRO"
        val obj = TaskRunner.airportFromIata(map)
        if (obj != null) {
            Log.d("outputnew", obj.toString())
        }
        */
    }

    fun onClickLogin(view : View) {
        val email = emailEt.text.toString()
        val password = passwordEt.text.toString()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val intent = Intent(this, LoggedActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    baseContext, "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
    }

    fun onClickSignUp(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}
