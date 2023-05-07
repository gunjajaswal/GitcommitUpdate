package com.example.gotour.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gotour.databinding.HotelsLayoutBinding
import com.example.gotour.models.HotelsNear


class HotelsAdapter(
    private val listener: (HotelsNear) -> Unit
) : ListAdapter<HotelsNear, HotelsAdapter.ViewHolder>(HotelDiff()) {

    class ViewHolder(private val binding: HotelsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hotel: HotelsNear) {
            binding.hotel = hotel
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HotelsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        Log.d("HomeFragment", "onViewCreated: ${getItem(position)}")
        holder.itemView.setOnClickListener {
            listener(getItem(position))
        }
    }

    class HotelDiff : DiffUtil.ItemCallback<HotelsNear>() {
        override fun areItemsTheSame(oldItem: HotelsNear, newItem: HotelsNear): Boolean {
            return oldItem == newItem


        }

        override fun areContentsTheSame(oldItem: HotelsNear, newItem: HotelsNear): Boolean {
            return oldItem.name == newItem.name

        }
    }


}