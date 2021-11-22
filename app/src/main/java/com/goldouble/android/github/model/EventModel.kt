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
        EventType.CommitCommentEvent -> null
        EventType.CreateEvent -> "${payload.refType}: ${payload.description ?: "No description"}"
        EventType.DeleteEvent -> null
        EventType.ForkEvent -> payload.forkee?.fullName
        EventType.GollumEvent -> null
        EventType.IssueCommentEvent -> payload.issue?.title
        EventType.IssuesEvent -> "${payload.action}: ${payload.issue?.title}"
        EventType.MemberEvent -> "${payload.action}: ${payload.member?.login}"
        EventType.PublicEvent -> null
        EventType.PullRequestEvent -> null
        EventType.PullRequestReviewEvent -> null
        EventType.PullRequestReviewCommentEvent -> null
        EventType.PushEvent -> payload.commits?.fold("") { acc, commit -> "$acc${commit.message}\n----------\n" }
        EventType.ReleaseEvent -> null
        EventType.SponsorshipEvent -> null
        EventType.WatchEvent -> payload.action
    }

    fun toggle() {
        isExpanded = !isExpanded
        notifyChange()
    }
}