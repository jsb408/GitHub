package com.goldouble.android.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.flowable
import com.goldouble.android.github.kPageSize
import com.goldouble.android.github.view.adapter.pagingsource.SearchResultPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SearchViewModel : ViewModel() {
    var inputKeyword: String = ""

    private val mSearchKeyword = MutableLiveData<String>()
    val searchKeyword: LiveData<String> = mSearchKeyword

    private val mIsNoResult = MutableLiveData<Boolean>()
    val isNoResult: LiveData<Boolean> = mIsNoResult

    private val mIsLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = mIsLoading

    @ExperimentalCoroutinesApi
    val flowable get() = Pager(PagingConfig(kPageSize)) {
        SearchResultPagingSource(searchKeyword.value!!)
    }.flowable.cachedIn(viewModelScope)

    private fun startLoading() {
        mIsLoading.value = true
    }

    fun stopLoading() {
        mIsLoading.value = false
    }

    fun setResult(isEmpty: Boolean) {
        mIsNoResult.postValue(isEmpty)
    }

    fun search() {
        startLoading()
        mSearchKeyword.value = inputKeyword
    }
}
