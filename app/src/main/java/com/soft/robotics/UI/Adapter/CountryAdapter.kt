package com.soft.robotics.UI.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.soft.robotics.Data.Local.Entities.Country
import com.soft.robotics.databinding.ItemCountryBinding

class CountryAdapter : ListAdapter<Country, CountryAdapter.CountryViewHolder>(CountryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CountryViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            binding.countryInfo.text = "${country.name}, ${country.code}"
        }
    }
}

class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}