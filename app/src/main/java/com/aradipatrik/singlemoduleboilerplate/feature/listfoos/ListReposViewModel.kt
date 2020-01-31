package com.aradipatrik.singlemoduleboilerplate.feature.listfoos

import com.airbnb.mvrx.*
import com.aradipatrik.singlemoduleboilerplate.MvRxViewModel
import com.aradipatrik.singlemoduleboilerplate.data.RepoRepository
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers

data class ListReposState(
    val repoList: List<LocalRepo> = emptyList(),
    val repoListRequest: Async<List<LocalRepo>> = Uninitialized,
    val syncRequest: Async<Unit> = Uninitialized
) : MvRxState

class ListReposViewModel @AssistedInject constructor(
    @Assisted state: ListReposState,
    private val repoRepository: RepoRepository
) : MvRxViewModel<ListReposState>(state) {

    @AssistedInject.Factory
    interface Factory {
        fun create(state: ListReposState): ListReposViewModel
    }

    companion object : MvRxViewModelFactory<ListReposViewModel, ListReposState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: ListReposState
        ): ListReposViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<ListReposFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }

    init {
        fetchRepos()
    }

    private fun fetchRepos() {
        withState {
            repoRepository
                .getAllRepos()
                .subscribeOn(Schedulers.io())
                .execute {
                    copy(
                        repoList = it() ?: repoList,
                        repoListRequest = it
                    )
                }

            repoRepository.syncRepos()
                .subscribeOn(Schedulers.io())
                .execute {
                    copy(
                        syncRequest = it
                    )
                }
        }
    }

}