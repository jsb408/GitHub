package com.goldouble.android.github.retrofit.service

import com.goldouble.android.github.model.EventModel
import com.goldouble.android.github.model.InfoModel
import com.goldouble.android.github.model.RepositoryModel
import com.goldouble.android.github.retrofit.RetrofitService
import com.goldouble.android.github.retrofit.response.GetProfileResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface GitHubService {
    @Headers("Authrization: token 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")

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

    @GET("/users/{username}/events")
    fun getEvents(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Single<List<EventModel>>
}
