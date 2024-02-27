package hr.k33zo.spacex.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.k33zo.spacex.adapter.MissionPagerAdapter
import hr.k33zo.spacex.databinding.ActivityMissionsPagerBinding
import hr.k33zo.spacex.framework.fetchMissions
import hr.k33zo.spacex.model.MissionItem

const val MISSIONS_POSITION = "hr.k33zo.spacex.mission_pos"

class MissionsPagerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMissionsPagerBinding
    private lateinit var missions: MutableList<MissionItem>
    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMissionsPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPager()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPager() {
        missions = fetchMissions()
        position = intent.getIntExtra(MISSIONS_POSITION, position)
        binding.viewPagerMissions.adapter = MissionPagerAdapter(this, missions)
        binding.viewPagerMissions.currentItem = position
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}