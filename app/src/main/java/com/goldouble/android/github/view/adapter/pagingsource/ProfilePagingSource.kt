package com.goldouble.android.github.view.adapter.pagingsource

import android.util.Log
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.goldouble.android.github.model.DetailModel
import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.retrofit.RetrofitService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class ProfilePagingSource(private val username: String) : RxPagingSource<Int, DetailModel>() {
    private val refreshKey = -1

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, DetailModel>> {
        val page = params.key ?: refreshKey
        val singleData = when (page) {
            -1 -> RetrofitService.gitHub.getInfo(username)
            0 -> RetrofitService.gitHub.getRepository(username)
            else -> RetrofitService.gitHub.getEvents(username, page)
        }

        return singleData
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, DetailModel>> { result ->
                val data = when (result) {
                    is InfoModel -> listOf(result)
                    else -> result as List<DetailModel>
                }

                LoadResult.Page(
                    data = data,
                    prevKey = if(page > refreshKey) page.minus(1) else null,
                    nextKey = if(data.isNotEmpty()) page.plus(1) else null
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

    override fun getRefreshKey(state: PagingState<Int, DetailModel>): Int {
        return refreshKey
    }
}