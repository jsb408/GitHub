package com.goldouble.android.github.retrofit.response

import com.goldouble.android.github.model.ProfileModel
import com.google.gson.annotations.SerializedName

data class GetProfileResponse (
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    val items: List<ProfileModel>
)