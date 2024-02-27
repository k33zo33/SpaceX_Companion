package hr.k33zo.spacex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.k33zo.spacex.adapter.RocketPagerAdapter
import hr.k33zo.spacex.databinding.ActivityRocketsPagerBinding
import hr.k33zo.spacex.framework.fetchRockets
import hr.k33zo.spacex.model.RocketItem

const val ROCKETS_POSITION = "hr.k33zo.spacex.rocket_pos"

class RocketsPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRocketsPagerBinding
    private lateinit var rockets: MutableList<RocketItem>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRocketsPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        rockets = fetchRockets()
        position = intent.getIntExtra(ROCKETS_POSITION, position)
        binding.viewPagerRockets.adapter = RocketPagerAdapter(this, rockets)
        binding.viewPagerRockets.currentItem = position
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}