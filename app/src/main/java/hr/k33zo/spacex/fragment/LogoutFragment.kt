package hr.k33zo.spacex.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hr.k33zo.spacex.activity.LoginActivity


class LogoutFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        auth.signOut()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

}