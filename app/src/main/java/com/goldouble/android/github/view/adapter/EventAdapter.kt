package com.goldouble.android.github.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.github.databinding.ItemEventProfileBinding
import com.goldouble.android.github.model.EventModel

class EventAdapter : PagingDataAdapter<EventModel, EventAdapter.EventViewHolder>(EventComparator) {
    object EventComparator : DiffUtil.ItemCallback<EventModel>() {
        override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            ItemEventProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(private val binding: ItemEventProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: EventModel?) {
            binding.event = event
        }
    }
}