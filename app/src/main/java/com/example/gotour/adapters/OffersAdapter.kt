package com.example.gotour.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gotour.databinding.OffersLayoutBinding
import com.example.gotour.models.Offer

class OffersAdapter(
    private val listener: (Offer) -> Unit
) : ListAdapter<Offer, OffersAdapter.ViewHolder>(OfferDiff()) {
    class ViewHolder(private val binding: OffersLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: Offer) {
            binding.offer = offer
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            OffersLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener { listener(getItem(position)) }
    }

    class OfferDiff : DiffUtil.ItemCallback<Offer>() {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.discount == newItem.discount
        }

    }

}



