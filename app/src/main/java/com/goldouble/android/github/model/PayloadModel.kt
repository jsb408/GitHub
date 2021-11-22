package com.goldouble.android.github.model

import com.google.gson.annotations.SerializedName

data class PayloadModel(
    // region PushEvent
    val commits: List<CommitModel>? = null,
    // endregion

    // region CreateEvent
    @SerializedName("ref_type")
    val refType: String? = null,

    val description: String? = null,
    // endregion

    // region WatchEvent, MemberEvent, IssuesEvent
    val action: String? = null,
    // endregion

    // region MemberEvent
    val member: ProfileModel? = null,
    // endregion

    // region ForkEvent
    val forkee: ForkeeModel? = null,
    // endregion

    // region IssueCommentEvent, IssuesEvent
    val issue: IssueModel? = null
    // endregion
)