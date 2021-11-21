package com.goldouble.android.github.retrofit.service

import com.goldouble.android.github.kClientId
import com.goldouble.android.github.model.EventModel
import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.model.RepositoryModel
import com.goldouble.android.github.retrofit.RetrofitService
import com.goldouble.android.github.retrofit.response.GetProfileResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface GitHubService {
    @GET("/search/users")
    fun getUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Header("Authorization") myClientId: String = RetrofitService.auth
    ): Single<GetProfileResponse>

    @GET("/users/{username}")
    fun getInfo(
        @Path("username") username: String,
        @Header("Authorization") myClientId: String = RetrofitService.auth
    ): Single<InfoModel>

    @GET("/users/{username}/repos")
    fun getRepository(
        @Path("username") username: String,
        @Query("sort") sort: String = "updated",
        @Query("per_page") perPage: Int = 3,
        @Header("Authorization") myClientId: String = RetrofitService.auth
    ): Single<List<RepositoryModel>>

    @GET("/users/{username}/events")
    fun getEvents(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Header("Authorization") myClientId: String = RetrofitService.auth
    ): Single<List<EventModel>>
}
