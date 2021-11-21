package com.goldouble.android.github.model

import com.google.gson.annotations.SerializedName

data class RepositoryModel(
    override val id: Long = 0,
    val name: String = "",
    val description: String? = null,
    val language: String = "",

    @SerializedName("stargazers_count")
    val stargazersCount: Int = 0
) : DetailModel