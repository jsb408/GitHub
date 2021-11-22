package com.goldouble.android.github.model

import com.google.gson.annotations.SerializedName

data class PayloadModel(
    // region PushEvent
    val commits: List<CommitModel>? = null,
    // endregion

    // region CreateEvent, DeleteEvent
    val ref: String? = null,

    @SerializedName("ref_type")
    val refType: String? = null,
    val description: String? = null,
    // endregion

    // region WatchEvent, MemberEvent, IssuesEvent, PullRequestEvent, PullRequestReviewEvent,
    // PullRequestReviewCommentEvent, ReleaseEvent
    val action: String? = null,
    // endregion

    // region MemberEvent
    val member: ProfileModel? = null,
    // endregion

    // region ForkEvent
    val forkee: ForkeeModel? = null,
    // endregion

    // region IssueCommentEvent, IssuesEvent
    val issue: IssueModel? = null,
    // endregion

    // region PullRequestEvent
    @SerializedName("pull_request")
    val pullRequest: PullRequestModel? = null,
    // endregion

    // region ReleaseEvent
    val releaseModel: ReleaseModel? = null,
    // endregion

    // region GollumEvent
    val pages: List<PageModel>? = null
    // endregion
)