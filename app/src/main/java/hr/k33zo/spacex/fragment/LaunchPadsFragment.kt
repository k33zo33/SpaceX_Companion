package hr.k33zo.spacex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.k33zo.spacex.adapter.LaunchPadAdapter
import hr.k33zo.spacex.databinding.FragmentLaunchPadsBinding
import hr.k33zo.spacex.framework.fetchLaunchPads
import hr.k33zo.spacex.model.LaunchPadItem


class LaunchPadsFragment : Fragment() {

   private lateinit var binding: FragmentLaunchPadsBinding
   private lateinit var launchPadItems: MutableList<LaunchPadItem>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        launchPadItems = requireContext().fetchLaunchPads()
        binding = FragmentLaunchPadsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvLaunchPads.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = LaunchPadAdapter(requireContext(), launchPadItems)
        }
    }


}