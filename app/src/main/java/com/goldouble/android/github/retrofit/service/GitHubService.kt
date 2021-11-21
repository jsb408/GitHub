package com.goldouble.android.github.retrofit.service

import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.model.RepositoryModel
import com.goldouble.android.github.retrofit.response.GetProfileResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("/search/users")
    fun getUsers(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Single<GetProfileResponse>

    @GET("/users/{username}")
    fun getInfo(
        @Path("username") username: String
    ): Single<InfoModel>

    @GET("/users/{username}/repos")
    fun getRepository(
        @Path("username") username: String,
        @Query("sort") sort: String = "updated",
        @Query("per_page") perPage: Int = 3
    ): Single<List<RepositoryModel>>
}
