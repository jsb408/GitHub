package com.goldouble.android.github.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.github.R
import com.goldouble.android.github.databinding.ItemEventProfileBinding
import com.goldouble.android.github.databinding.ItemInfoProfileBinding
import com.goldouble.android.github.databinding.ItemRepositoryProfileBinding
import com.goldouble.android.github.model.DetailModel
import com.goldouble.android.github.model.EventModel
import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.model.RepositoryModel

class ProfileAdapter: PagingDataAdapter<DetailModel, ProfileAdapter.ProfileViewHolder>(ProfileComparator) {
    object ProfileComparator : DiffUtil.ItemCallback<DetailModel>() {
        override fun areItemsTheSame(oldItem: DetailModel, newItem: DetailModel): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: DetailModel, newItem: DetailModel): Boolean = oldItem.id == newItem.id
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is InfoModel -> R.layout.item_info_profile
        is RepositoryModel-> R.layout.item_repository_profile
        else -> R.layout.item_event_profile
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setInfo(info: InfoModel) {
        snapshot().items.toMutableList().add(0, info)
        notifyItemInserted(0)
    }

    inner class ProfileViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailModel?) {
            when(binding) {
                is ItemInfoProfileBinding -> {
                    val info = data as? InfoModel
                    binding.info = info
                }
                is ItemRepositoryProfileBinding -> {
                    val repository = data as? RepositoryModel
                    binding.repository = repository
                }
                is ItemEventProfileBinding -> {
                    val event = data as? EventModel
                    Log.d(ProfileAdapter::class.simpleName, event.toString())
                    binding.event = event
                }
            }
        }
    }
}
