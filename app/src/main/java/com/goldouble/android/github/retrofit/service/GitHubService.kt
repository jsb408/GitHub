package com.goldouble.android.github.retrofit.service

import com.goldouble.android.github.retrofit.response.GetProfileResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("/search/users")
    fun getUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Single<GetProfileResponse>
}
