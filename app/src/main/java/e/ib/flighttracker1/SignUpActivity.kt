package e.ib.flighttracker1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var emailEt : EditText
    private lateinit var pass1Et : EditText
    private lateinit var pass2Et : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        emailEt = findViewById(R.id.email_et)
        pass1Et = findViewById(R.id.pass1_et)
        pass2Et = findViewById(R.id.pass2_et)

    }

    fun onSubmit(view : View) {
        val email = emailEt.text.toString()
        val pass1 = pass1Et.text.toString()
        val pass2 = pass1Et.text.toString()
        if (pass1 == pass2) {
            auth.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        val intent = Intent(this, LoggedActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Something went wrong. Please try again. ", Toast.LENGTH_LONG).show()
                    }
                    // ...
                }
        }
        Toast.makeText(this, "Passwords must match. ", Toast.LENGTH_LONG).show()
    }
}
