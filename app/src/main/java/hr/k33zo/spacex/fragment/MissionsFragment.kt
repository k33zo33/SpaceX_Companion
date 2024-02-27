package hr.k33zo.spacex.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import hr.k33zo.spacex.adapter.MissionAdapter
import hr.k33zo.spacex.databinding.FragmentMissionsBinding
import hr.k33zo.spacex.framework.fetchMissions
import hr.k33zo.spacex.model.MissionItem


class MissionsFragment : Fragment() {

    private lateinit var binding: FragmentMissionsBinding
    private lateinit var missionItems: MutableList<MissionItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        missionItems = requireContext().fetchMissions()
        binding = FragmentMissionsBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMissions.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MissionAdapter(requireContext(), missionItems)
        }
    }


}