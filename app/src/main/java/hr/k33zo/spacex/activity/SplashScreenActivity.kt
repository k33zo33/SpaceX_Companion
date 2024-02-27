package hr.k33zo.spacex.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.k33zo.spacex.R
import hr.k33zo.spacex.api.SpaceXWorker
import hr.k33zo.spacex.databinding.ActivitySplashScreenBinding
import hr.k33zo.spacex.framework.applyAnimation
import hr.k33zo.spacex.framework.callDelayed
import hr.k33zo.spacex.framework.getBooleanPreference
import hr.k33zo.spacex.framework.isOnline
import hr.k33zo.spacex.framework.startActivity
import java.util.Locale

private const val DELAY = 3000L

const val DATA_IMPORTED = "hr.k33zo.spacex.data_imported"

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocaleFromPreference()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        startAnimations()
        redirect()

    }

    private fun setLocaleFromPreference() {
        val savedLanguagePosition = getSharedPreferences(
            "MyPrefs",
            Context.MODE_PRIVATE
        ).getInt("language_position", 0)

        val selectedLanguage = resources.getStringArray(R.array.languages)[savedLanguagePosition]
        setLocale(selectedLanguage)
    }

    private fun setLocale(language: String) {
        val locale = when (language) {
            "English" -> Locale("en")
            "Croatian" -> Locale("hr")
            // Add more languages as needed
            else -> Locale.getDefault()
        }

        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }



    private fun startAnimations() {
        binding.tvSplash.applyAnimation(R.anim.blink)
        binding.ivSplash.applyAnimation(R.anim.fade)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY){
                startActivity<HostActivity>()
            }
        }else{
            if (isOnline()){
                WorkManager.getInstance(this).apply {
                    enqueueUniqueWork(
                        DATA_IMPORTED,
                        ExistingWorkPolicy.KEEP,
                        OneTimeWorkRequest.Companion.from(SpaceXWorker::class.java))
                }
            }else{
                binding.tvSplash.text= getString(R.string.no_connection)
                callDelayed(DELAY){
                    finish()
                }
            }
        }
    }

}