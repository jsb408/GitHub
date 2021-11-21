package com.goldouble.android.github.retrofit

import com.goldouble.android.github.kBaseUrl
import com.goldouble.android.github.retrofit.service.GitHubService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    companion object {
        private val baseUrl = kBaseUrl

        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val gitHub: GitHubService = retrofit.create(GitHubService::class.java)
    }
}