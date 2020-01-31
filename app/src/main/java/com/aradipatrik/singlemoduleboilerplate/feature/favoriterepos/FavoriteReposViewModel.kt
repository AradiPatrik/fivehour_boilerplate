package com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos

import com.airbnb.mvrx.*
import com.aradipatrik.singlemoduleboilerplate.MvRxViewModel
import com.aradipatrik.singlemoduleboilerplate.data.RepoRepository
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers

data class FavoriteReposState(
    val favoriteRepos: List<LocalRepo> = emptyList(),
    val favoriteFoosRequest: Async<List<LocalRepo>> = Uninitialized,
    val syncRequest: Async<Unit> = Uninitialized
): MvRxState

class FavoriteReposViewModel @AssistedInject constructor(
    @Assisted state: FavoriteReposState,
    private val repoRepository: RepoRepository
) : MvRxViewModel<FavoriteReposState>(state) {

    @AssistedInject.Factory
    interface Factory {
        fun create(state: FavoriteReposState): FavoriteReposViewModel
    }

    companion object: MvRxViewModelFactory<FavoriteReposViewModel, FavoriteReposState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: FavoriteReposState
        ): FavoriteReposViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<FavoriteReposFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }

    init {
        logStateChanges()
        fetchFavorites()
    }

    private fun fetchFavorites() {
        repoRepository.getFavoriteRepos()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(favoriteRepos = it() ?: favoriteRepos, favoriteFoosRequest = it)
            }

        repoRepository.syncRepos()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(syncRequest = it)
            }
    }
}