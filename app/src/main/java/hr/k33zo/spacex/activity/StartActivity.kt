package hr.k33zo.spacex.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val user = auth.currentUser
        if (user!=null){
            //Log.e("user", user.toString())
            val intent = Intent(this, SplashScreenActivity::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}