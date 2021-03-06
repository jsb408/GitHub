package com.goldouble.android.github.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileModel(
    val id: Long = 0,
    val login: String = "",

    @SerializedName("avatar_url")
    val avatarURL: String = ""
) : Serializable