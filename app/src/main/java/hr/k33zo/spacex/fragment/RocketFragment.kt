package hr.k33zo.spacex.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.k33zo.spacex.adapter.RocketAdapter
import hr.k33zo.spacex.databinding.FragmentRocketBinding
import hr.k33zo.spacex.framework.fetchRockets
import hr.k33zo.spacex.model.RocketItem


class RocketFragment : Fragment() {

    private lateinit var binding: FragmentRocketBinding
    private lateinit var rocketItems: MutableList<RocketItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rocketItems = requireContext().fetchRockets()
        binding = FragmentRocketBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvRockets.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = RocketAdapter(requireContext(), rocketItems)
        }
    }


}