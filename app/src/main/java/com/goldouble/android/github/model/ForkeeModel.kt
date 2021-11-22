package com.goldouble.android.github.model

import com.google.gson.annotations.SerializedName

data class ForkeeModel(
    val name: String,

    @SerializedName("full_name")
    val fullName: String,
    val owner: ProfileModel
)
