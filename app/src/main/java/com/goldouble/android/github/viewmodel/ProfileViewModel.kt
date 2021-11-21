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
import com.goldouble.android.github.retrofit.RetrofitService
import com.goldouble.android.github.view.adapter.pagingsource.ProfilePagingSource
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class ProfileViewModel(private val username: String) : ViewModel() {
    private val mInfoModel = MutableLiveData<InfoModel>()
    val infoModel: LiveData<InfoModel> = mInfoModel

    @ExperimentalCoroutinesApi
    val flowable get() = Pager(PagingConfig(kPageSize)) {
        ProfilePagingSource(username)
    }.flowable.cachedIn(viewModelScope)

    private fun getInfo(username: String) {
        RetrofitService.gitHub.getInfo(username)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    mInfoModel.postValue(it)
                },
                { e ->
                    Log.e(ProfileViewModel::class.simpleName, e.localizedMessage, e)
                }
            )
    }

    private fun getRepository(username: String) {

    }
}
