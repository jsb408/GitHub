package com.goldouble.android.github.view.adapter.pagingsource

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.goldouble.android.github.model.DetailModel
import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.model.RepositoryModel
import com.goldouble.android.github.retrofit.RetrofitService
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

class ProfilePagingSource(private val username: String) : RxPagingSource<Int, DetailModel>() {
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, DetailModel>> {
        val singleData = when (params.key ?: -1) {
            -1 -> RetrofitService.gitHub.getInfo(username)
            0 -> RetrofitService.gitHub.getRepository(username)
            else -> RetrofitService.gitHub.getRepository(username)
        }

        return singleData
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, DetailModel>> { result ->
                LoadResult.Page(
                    data = when (result) {
                        is InfoModel -> listOf(result)
                        else -> result as List<RepositoryModel>
                    },
                    prevKey = params.key,
                    nextKey = params.key?.plus(1)
                )
            }
            .onErrorReturn { e ->
                when (e) {
                    is IOException -> LoadResult.Error(e)
                    is HttpException -> LoadResult.Error(e)
                    else -> throw e
                }
            }
    }

    override fun getRefreshKey(state: PagingState<Int, DetailModel>): Int {
        return 0
    }
}