package com.soft.robotics.UI.View

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.soft.robotics.R
import com.soft.robotics.UI.Adapter.CountryAdapter
import com.soft.robotics.UI.ViewModel.CountryListViewModel
import com.soft.robotics.databinding.FragmentCountryListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryListFragment : Fragment(R.layout.fragment_country_list) {

    private val viewModel: CountryListViewModel by viewModels()
    private var _binding: FragmentCountryListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCountryListBinding.bind(view)

        val adapter = CountryAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        observeViewModel(adapter)

        viewModel.networkStatus.observe(viewLifecycleOwner, Observer { isNetworkAvailable ->
            if (isNetworkAvailable) {
                viewModel.refreshCountries()
            } else {
                Toast.makeText(requireContext(), "No network available. Showing Local Data.", Toast.LENGTH_LONG).show()
                viewModel.fetchLocalCountries()
            }
        })
    }

    private fun observeViewModel(adapter: CountryAdapter) {
        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
            if (countries.isNullOrEmpty()) {
                binding.noDataTextView.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                Toast.makeText(requireContext(), "No data available. Connect to the internet to fetch data.", Toast.LENGTH_LONG).show()
            } else {
                adapter.submitList(countries)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
