package com.goldouble.android.github.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.github.databinding.ItemRepositoryProfileBinding
import com.goldouble.android.github.model.RepositoryModel

class RepositoryAdapter(private val repositories: List<RepositoryModel>) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {
    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ItemRepositoryProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    inner class RepositoryViewHolder(private val binding: ItemRepositoryProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: RepositoryModel) {
            binding.repository = repository
        }
    }
}