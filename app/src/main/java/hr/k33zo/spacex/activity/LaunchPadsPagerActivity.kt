package hr.k33zo.spacex.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.k33zo.spacex.adapter.LaunchPadPagerAdapter
import hr.k33zo.spacex.databinding.ActivityLauchPadsPagerBinding
import hr.k33zo.spacex.framework.fetchLaunchPads
import hr.k33zo.spacex.model.LaunchPadItem

const val LAUCH_PADS_POSITION = "hr.k33zo.spacex.launch_pad_pos"

class LaunchPadsPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLauchPadsPagerBinding
    private lateinit var launchPads: MutableList<LaunchPadItem>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauchPadsPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

       // binding.viewPagerLaunchPads.isUserInputEnabled = false
    }

    private fun initPager() {
        launchPads = fetchLaunchPads()
        position = intent.getIntExtra(LAUCH_PADS_POSITION,position)
        binding.viewPagerLaunchPads.adapter = LaunchPadPagerAdapter(this,  launchPads)
        binding.viewPagerLaunchPads.currentItem = position


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}