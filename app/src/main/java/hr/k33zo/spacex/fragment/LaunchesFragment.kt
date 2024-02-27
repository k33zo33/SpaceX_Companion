package hr.k33zo.spacex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.k33zo.spacex.adapter.LaunchAdapter
import hr.k33zo.spacex.databinding.FragmentLaunchesBinding
import hr.k33zo.spacex.framework.fetchLaunches
import hr.k33zo.spacex.model.LaunchItem


class LaunchesFragment : Fragment() {

    private lateinit var binding: FragmentLaunchesBinding
    private lateinit var launchItems: MutableList<LaunchItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        launchItems = requireContext().fetchLaunches()
        binding = FragmentLaunchesBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvLaunches.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = LaunchAdapter(requireContext(), launchItems)
        }
    }
}