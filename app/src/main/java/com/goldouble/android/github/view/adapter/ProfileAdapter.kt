package com.goldouble.android.github.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.github.R
import com.goldouble.android.github.databinding.ItemInfoProfileBinding
import com.goldouble.android.github.databinding.ItemRecyclerviewProfileBinding
import com.goldouble.android.github.model.EventModel
import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.model.RepositoryModel

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {
    private var infoModel: InfoModel? = null
    private var repositoryList: List<RepositoryModel>? = null

    private val eventAdapter = EventAdapter()

    override fun getItemCount(): Int = 3

    override fun getItemViewType(position: Int): Int =
        if(position == 0) R.layout.item_info_profile else R.layout.item_recyclerview_profile

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind()
    }

    fun submitEventData(lifecycle: Lifecycle, pagingData: PagingData<EventModel>) {
        eventAdapter.submitData(lifecycle, pagingData)
    }

    fun refresh() {
        eventAdapter.refresh()
    }

    fun setUserData(infoModel: InfoModel) {
        this.infoModel = infoModel
        notifyItemChanged(0)
    }

    fun setRepositoryList(repositoryList: List<RepositoryModel>) {
        this.repositoryList = repositoryList
        notifyItemChanged(1)
    }

    inner class ProfileViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            when(binding) {
                is ItemInfoProfileBinding -> binding.info = infoModel
                is ItemRecyclerviewProfileBinding -> {
                    if (bindingAdapterPosition == 1) {
                        binding.isLoading = repositoryList == null
                        binding.noResultText = if (repositoryList?.isEmpty() == true) "저장소가 없습니다." else null

                        repositoryList?.let {
                            binding.rvProfile.adapter = RepositoryAdapter(it)
                        }
                    } else {
                        eventAdapter.addLoadStateListener {
                            if (it.refresh is LoadState.NotLoading) {
                                binding.isLoading = false
                                if (eventAdapter.itemCount == 0) binding.noResultText = "이벤트가 없습니다."
                            }
                        }
                        binding.rvProfile.adapter = eventAdapter
                    }
                }
            }
        }
    }
}
