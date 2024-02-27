package hr.k33zo.spacex.activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import hr.k33zo.spacex.databinding.ActivityUploadImageBinding
import hr.k33zo.spacex.fragment.SettingsFragment

class UploadImageActivity : AppCompatActivity() {

    lateinit var binding : ActivityUploadImageBinding
    lateinit var imageUri : Uri
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelect.setOnClickListener {

            selectImage()
        }
        binding.btnUpload.setOnClickListener {
            uploadImage()
        }

    }
    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading file...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        auth = Firebase.auth
        val userId = auth.currentUser?.uid.toString()

        val storageReference = FirebaseStorage.getInstance().getReference("images/$userId")
        storageReference.putFile(imageUri)
            .addOnSuccessListener {
                binding.ivSelectedImage.setImageURI(imageUri)
                Toast.makeText(this@UploadImageActivity, "Successfuly uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()


        }
            .addOnFailureListener {
                Toast.makeText(this@UploadImageActivity, "Upload failed", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()

            }

        val settingsFragment = supportFragmentManager.findFragmentByTag("SettingsFragment") as? SettingsFragment
        settingsFragment?.refreshContent()
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            imageUri = data?.data!!
            Log.e("Image uri", imageUri.toString())
            binding.ivSelectedImage.setImageURI(imageUri)
        }
    }
}
