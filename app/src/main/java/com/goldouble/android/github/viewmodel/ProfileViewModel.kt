package com.goldouble.android.github.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.flowable
import com.goldouble.android.github.kPageSize
import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.model.RepositoryModel
import com.goldouble.android.github.retrofit.RetrofitService
import com.goldouble.android.github.view.adapter.ProfileAdapter
import com.goldouble.android.github.view.adapter.pagingsource.EventPagingSource
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ProfileViewModel(private val username: String) : ViewModel() {
    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    private val mUserInfo = MutableLiveData<InfoModel>()
    val userInfo: LiveData<InfoModel> = mUserInfo

    private val mRepositoryList = MutableLiveData<List<RepositoryModel>>()
    val repositoryList: LiveData<List<RepositoryModel>> = mRepositoryList

    @ExperimentalCoroutinesApi
    val flowable get() = Pager(PagingConfig(kPageSize)) {
        EventPagingSource(username)
    }.flowable.cachedIn(viewModelScope)

    fun startLoading() {
        mIsLoading.value = true
    }

    fun stopLoading() {
        mIsLoading.value = false
    }

    fun loadUserInfo() {
        RetrofitService.gitHub.getInfo(username)
            .observeOn(Schedulers.io())
            .subscribe(
                {
                    mUserInfo.postValue(it)
                }, { e ->
                    Log.e(ProfileAdapter::class.simpleName, e.localizedMessage, e)
                }
            )
    }

    fun loadRepositories() {
        RetrofitService.gitHub.getRepository(username)
            .observeOn(Schedulers.io())
            .subscribe(
                {
                    mRepositoryList.postValue(it)
                }, { e ->
                    Log.e(ProfileAdapter::class.simpleName, e.localizedMessage, e)
                }
            )
    }
}
