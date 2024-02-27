package hr.k33zo.spacex.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import hr.k33zo.spacex.activity.HostActivity
import hr.k33zo.spacex.R
import hr.k33zo.spacex.activity.SignUpActivity
import hr.k33zo.spacex.activity.UploadImageActivity
import java.util.Locale



class SettingsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var profilePicture: ImageView
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE
        )

        val languageSpinner = view.findViewById<Spinner>(R.id.spLanguage)
        val uploadProfilePicture = view.findViewById<Button>(R.id.btnUploadPicture)
        profilePicture = view.findViewById(R.id.ivProfilePicture)


        auth = Firebase.auth
        val userId = auth.currentUser?.uid.toString()

        loadProfilePicture(userId, profilePicture)

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            languageSpinner.adapter = adapter
        }

        val savedLanguagePosition = sharedPreferences.getInt("language_position", 0)
        languageSpinner.setSelection(savedLanguagePosition)

        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = parent?.getItemAtPosition(position).toString()
                setLocale(selectedLanguage, position)

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        uploadProfilePicture.setOnClickListener {
            val intent = Intent(context, UploadImageActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    private fun loadProfilePicture(userId: String, profilePicture: ImageView?) {

        val storageReference = FirebaseStorage.getInstance().getReference("images/$userId")

        // Get metadata of the file
        storageReference.metadata
            .addOnSuccessListener { metadata ->
                // Check if metadata exists
                if (metadata != null) {
                    // File exists, load profile picture
                    storageReference.downloadUrl
                        .addOnSuccessListener { uri ->
                            Picasso.get().load(uri).into(profilePicture)
                        }
                        .addOnFailureListener { exception ->
                            Log.e("SettingsFragment", "Error downloading image: ${exception.message}")
                        }
                } else {
                    // File does not exist
                    Log.d("SettingsFragment", "Profile picture does not exist for user $userId")
                    // Optionally, you can display a placeholder image here

                }
            }
            .addOnFailureListener { exception ->
                Log.e("SettingsFragment", "Error checking if image exists: ${exception.message}")
            }
    }

    private fun setLocale(language: String, position: Int) {
        val currentLanguagePosition = sharedPreferences.getInt("language_position", 0)
        if (position != currentLanguagePosition) {
            val locale = when (language) {
                "English" -> Locale("en")
                "Croatian" -> Locale("hr")
                // Add more languages as needed
                else -> Locale.getDefault()
            }
            Locale.setDefault(locale)
            val config = Configuration(resources.configuration)
            config.setLocale(locale)
            requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
            // Save selected language position in SharedPreferences
            sharedPreferences.edit().putInt("language_position", position).apply()

            (activity as? HostActivity)?.restartActivity()
        }
    }

    fun refreshContent(){
        val userId = Firebase.auth.currentUser?.uid ?: ""
        loadProfilePicture(userId, profilePicture)
    }
}