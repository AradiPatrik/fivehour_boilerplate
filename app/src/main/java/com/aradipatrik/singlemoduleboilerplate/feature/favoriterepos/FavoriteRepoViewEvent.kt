package com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos

import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo

sealed class FavoriteRepoViewEvent {
    data class FavoriteRepoClick(val repo: LocalRepo): FavoriteRepoViewEvent()
}
