package com.goldouble.android.github.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.github.databinding.ItemProfileSearchBinding
import com.goldouble.android.github.model.ProfileModel

class ProfileAdapter(
    private val onItemClickListener: (ProfileModel) -> Unit
) : PagingDataAdapter<ProfileModel, ProfileAdapter.ProfileViewHolder>(ProfileComparator) {
    object ProfileComparator : DiffUtil.ItemCallback<ProfileModel>() {
        override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            ItemProfileSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfileViewHolder(private val binding: ItemProfileSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: ProfileModel?) {
            binding.profile = profile

            binding.root.setOnClickListener { _ ->
                profile?.let { onItemClickListener(it) }
            }
        }
    }
}