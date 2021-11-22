package com.goldouble.android.github.view.adapter.pagingsource

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.goldouble.android.github.model.EventModel
import com.goldouble.android.github.retrofit.RetrofitService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class ProfilePagingSource(private val username: String) : RxPagingSource<Int, EventModel>() {
    private val refreshKey = 1

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, EventModel>> {
        val page = params.key ?: refreshKey

        return RetrofitService.gitHub.getEvents(username, page)
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, EventModel>> { result ->
                LoadResult.Page(
                    data = result,
                    prevKey = if(page > refreshKey) page.minus(1) else null,
                    nextKey = if(result.isNotEmpty()) page.plus(1) else null
                )
            }
            .onErrorReturn { e ->
                Log.e(ProfilePagingSource::class.simpleName, e.localizedMessage, e)
                when (e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, EventModel>): Int {
        return refreshKey
    }
}