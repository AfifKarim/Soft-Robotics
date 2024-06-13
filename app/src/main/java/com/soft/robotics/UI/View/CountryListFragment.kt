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

        viewModel.networkStatus.observe(viewLifecycleOwner, Observer { isNetworkAvailable ->
            if (isNetworkAvailable) {
                viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
                    if (countries.isNullOrEmpty()) {
                        viewModel.localCountries.observe(viewLifecycleOwner, Observer { localCountries ->
                            if (localCountries.isNullOrEmpty()) {
                                binding.noDataTextView.visibility = View.VISIBLE
                                binding.recyclerView.visibility = View.GONE
                                Toast.makeText(requireContext(), "Connect To Internet To Get Data.", Toast.LENGTH_LONG).show()
                            } else {
                                binding.noDataTextView.visibility = View.GONE
                                binding.recyclerView.visibility = View.VISIBLE
                                adapter.submitList(localCountries)
                                Toast.makeText(requireContext(), "No network available. Showing local data.", Toast.LENGTH_LONG).show()
                            }
                        })
                    } else {
                        binding.noDataTextView.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        adapter.submitList(countries)
                    }
                })
            } else {
                viewModel.fetchLocalCountries()
            }
        })

//        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->
//            if (countries.isNullOrEmpty()) {
//                binding.noDataTextView.visibility = View.VISIBLE
//                binding.recyclerView.visibility = View.GONE
//            } else {
//                binding.noDataTextView.visibility = View.GONE
//                binding.recyclerView.visibility = View.VISIBLE
//                adapter.submitList(countries)
//            }
//        })

//        viewModel.networkStatus.observe(viewLifecycleOwner, Observer { isNetworkAvailable ->
//            if (isNetworkAvailable) {
//                viewModel.refreshCountries()
//            } else {
//                viewModel.fetchLocalCountries()
//            }
//        })
//
//        viewModel.localCountries.observe(viewLifecycleOwner, Observer { localCountries ->
//            if (localCountries.isNullOrEmpty()) {
//                Toast.makeText(requireContext(), "Connect To Internet To Get Data.", Toast.LENGTH_LONG).show()
//            } else {
//                Toast.makeText(requireContext(), "No network available. Showing local data.", Toast.LENGTH_LONG).show()
//                adapter.submitList(localCountries)
//            }
//        })

//        viewModel.refreshCountries() // Fetch and update data on view created
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
