package com.goldouble.android.github.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.github.databinding.ItemResultSearchBinding
import com.goldouble.android.github.model.ProfileModel

class SearchResultAdapter(
    private val onItemClickListener: (String) -> Unit
) : PagingDataAdapter<ProfileModel, SearchResultAdapter.SearchResultViewHolder>(SearchResultComparator) {
    object SearchResultComparator : DiffUtil.ItemCallback<ProfileModel>() {
        override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(
            ItemResultSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SearchResultViewHolder(private val binding: ItemResultSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: ProfileModel?) {
            binding.profile = profile

            binding.root.setOnClickListener { _ ->
                profile?.let { onItemClickListener(it.login) }
            }
        }
    }
}