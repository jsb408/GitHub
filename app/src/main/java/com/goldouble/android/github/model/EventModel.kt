package com.goldouble.android.github.model

import androidx.databinding.BaseObservable

data class EventModel(
    val id: Long,
    val type: String = "",
    val payload: PayloadModel,
    val repo: RepositoryModel,
    val actor: ProfileModel
): BaseObservable() {
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
    var isExpanded: Boolean = false

    val payloadText get() = when(eventType) {
        EventType.CommitCommentEvent -> payload.action
        EventType.CreateEvent -> "${payload.refType}: ${payload.ref ?: payload.description ?: "No description."}"
        EventType.DeleteEvent -> "${payload.refType}: ${payload.ref}"
        EventType.ForkEvent -> payload.forkee?.fullName
        EventType.GollumEvent -> payload.pages?.fold("") { acc, page -> "$acc${page.title}\n" }
        EventType.IssueCommentEvent -> payload.issue?.title
        EventType.IssuesEvent -> "${payload.action}: ${payload.issue?.title}"
        EventType.MemberEvent -> "${payload.action}: ${payload.member?.login}"
        EventType.PublicEvent -> payload.action
        EventType.PullRequestEvent -> "${payload.action}: ${payload.pullRequest?.title}"
        EventType.PullRequestReviewEvent -> "${payload.action}: ${payload.pullRequest?.title}"
        EventType.PullRequestReviewCommentEvent -> "${payload.action}: ${payload.pullRequest?.title}"
        EventType.PushEvent -> payload.commits?.fold("") { acc, commit -> "$acc${commit.message}\n----------\n" }
        EventType.ReleaseEvent -> "${payload.action}: ${payload.releaseModel?.name}"
        EventType.SponsorshipEvent -> payload.action
        EventType.WatchEvent -> payload.action
    }

    fun toggle() {
        isExpanded = !isExpanded
        notifyChange()
    }
}