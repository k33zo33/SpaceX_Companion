package hr.k33zo.spacex.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import hr.k33zo.spacex.R
import hr.k33zo.spacex.databinding.ActivityHostBinding

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var navigationImage: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        binding = ActivityHostBinding.inflate(layoutInflater)
        auth = Firebase.auth
        setContentView(binding.root)
        initHamburgerMenu()
        initNavigation()

    }

    private fun initHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun initNavigation() {
        val userId = auth.currentUser?.uid.toString()
        val navController = Navigation.findNavController(this, R.id.navigationController)
        NavigationUI.setupWithNavController(binding.navigationView, navController)
        changeNavigationHeaderImage(userId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                toggleDrawer()
                return true
            }
            R.id.menuExit ->{
                exitApp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.exit)
            setMessage(getString(R.string.do_you_really_want_to_close_the_application))
            setIcon(R.drawable.exit)
            setCancelable(true)
            setNegativeButton(getString(R.string.cancel), null)
            setPositiveButton("OK"){_,_->finish()}
            show()
        }
    }

    private fun toggleDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()
        }else{
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    fun restartActivity() {
        val intent = Intent(this, HostActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    fun changeNavigationHeaderImage(userId: String){
        val headerView = binding.navigationView.getHeaderView(0)
        val navigationImage = headerView.findViewById<ImageView>(R.id.ivNavigationImage)

        val storageReference = FirebaseStorage.getInstance().getReference("images/$userId")

        storageReference.metadata
            .addOnSuccessListener { metadata ->
                // Check if metadata exists
                if (metadata != null) {
                    // File exists, load profile picture
                    storageReference.downloadUrl
                        .addOnSuccessListener { uri ->
                            Picasso.get().load(uri).into(navigationImage)
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

}