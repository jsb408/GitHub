package com.goldouble.android.github.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava3.cachedIn
import androidx.paging.rxjava3.flowable
import com.goldouble.android.github.kPageSize
import com.goldouble.android.github.view.adapter.pagingsource.ProfilePagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SearchViewModel : ViewModel() {
    var searchKeyword: String = ""

    @ExperimentalCoroutinesApi
    val flow get() = Pager(PagingConfig(kPageSize)) {
        ProfilePagingSource(searchKeyword)
    }.flowable.cachedIn(viewModelScope)
}
