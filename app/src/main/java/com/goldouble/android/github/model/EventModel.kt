package com.goldouble.android.github.model

data class EventModel(
    override val id: Long = 0,

    val type: String = "",
    // val payload: PayloadModel,
    val repo: RepositoryModel,
    val actor: ProfileModel
): DetailModel {
    enum class EventType(val title: String) {
        CommitCommentEvent("Comment"),
        CreateEvent("Create"),
        DeleteEvent("Delete"),
        ForkEvent("Fork"),
        GollumEvent("Gollum"),
        IssueCommentEvent("Issue Comment"),
        IssuesEvent("Issues"),
        MemberEvent("Member"),
        PublicEvent("Public"),
        PullRequestEvent("PR"),
        PullRequestReviewEvent("PR Review"),
        PullRequestReviewCommentEvent("PR Review Comment"),
        PushEvent("Push"),
        ReleaseEvent("Release"),
        SponsorshipEvent("Sponsorship"),
        WatchEvent("Watch")
    }

    val eventType get() = EventType.valueOf(type)
}