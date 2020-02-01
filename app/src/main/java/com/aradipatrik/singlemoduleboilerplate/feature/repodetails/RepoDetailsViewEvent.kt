package com.aradipatrik.singlemoduleboilerplate.feature.repodetails

sealed class RepoDetailsViewEvent {
    data class FavoriteClicked(val newFavoriteState: Boolean): RepoDetailsViewEvent()
}