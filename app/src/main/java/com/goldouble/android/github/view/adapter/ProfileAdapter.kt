package com.goldouble.android.github.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.goldouble.android.github.R
import com.goldouble.android.github.databinding.ItemInfoProfileBinding
import com.goldouble.android.github.databinding.ItemRecyclerviewProfileBinding
import com.goldouble.android.github.model.*
import com.goldouble.android.github.retrofit.RetrofitService
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileAdapter(private val username: String): RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {
    private var infoModel: InfoModel? = null
    private var repositoryList: List<RepositoryModel> = emptyList()

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
                is ItemInfoProfileBinding -> {
                    binding.info = infoModel
                }
                is ItemRecyclerviewProfileBinding -> {
                    if (bindingAdapterPosition == 1) {
                        RetrofitService.gitHub.getRepository(username)
                            .observeOn(Schedulers.io())
                            .subscribe(
                                {
                                    binding.rvProfile.adapter = RepositoryAdapter(it)
                                }, { e ->
                                    Log.e(ProfileAdapter::class.simpleName, e.localizedMessage, e)
                                }
                            )
                    } else {
                        binding.rvProfile.adapter = eventAdapter
                    }
                }
            }
        }
    }
}
