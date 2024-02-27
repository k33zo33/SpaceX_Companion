package hr.k33zo.spacex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.k33zo.spacex.adapter.LaunchPagerAdapter
import hr.k33zo.spacex.databinding.ActivityLaunchesPagerBinding
import hr.k33zo.spacex.framework.fetchLaunches
import hr.k33zo.spacex.model.LaunchItem

const val POSITION = "hr.k33zo.spacex.item_position"

class LaunchesPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLaunchesPagerBinding
    private lateinit var launches: MutableList<LaunchItem>
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchesPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        launches = fetchLaunches()
        position = intent.getIntExtra(POSITION, position)
        binding.viewPager.adapter = LaunchPagerAdapter(this, launches)
        binding.viewPager.currentItem = position
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}