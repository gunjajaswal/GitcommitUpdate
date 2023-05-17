package com.example.gotour.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gotour.databinding.HomeadapterfliperBinding
import com.example.gotour.models.Place

class PlaceAdapter (private val listener: (Place) -> Unit): ListAdapter<Place, PlaceAdapter.ViewHolder>(PlaceDiff()){
    class ViewHolder(private val binding: HomeadapterfliperBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
            binding.place = place
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeadapterfliperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d("HomeFragment", "onViewCreated: ${getItem(position)}")
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

    class PlaceDiff : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.name == newItem.name
        }

    }
}