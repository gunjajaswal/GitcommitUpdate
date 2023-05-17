package com.example.gotour.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.ViewGroup

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gotour.databinding.CityLayoutBinding
import com.example.gotour.models.CityInformation

class CityAdapter(private val listener:(CityInformation)->Unit):
    ListAdapter<CityInformation, CityAdapter.ViewHolder>(CityDiff()) {
    class ViewHolder(private val binding:CityLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(city:CityInformation){
            binding.city=city
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=CityLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position))
        }
    }



    class CityDiff:DiffUtil.ItemCallback<CityInformation>(){
        override fun areItemsTheSame(oldItem: CityInformation, newItem: CityInformation): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: CityInformation, newItem: CityInformation): Boolean {
            return oldItem.name==newItem.name
        }

    }
}

