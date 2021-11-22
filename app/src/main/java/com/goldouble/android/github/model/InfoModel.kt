package com.goldouble.android.github.model

import com.google.gson.annotations.SerializedName

data class InfoModel (
    val id: Long,
    val login: String = "",
    
    @SerializedName("avatar_url")
    val avatarURL: String = "",

    val bio: String? = null
)