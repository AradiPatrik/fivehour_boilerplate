package com.aradipatrik.singlemoduleboilerplate.feature.repodetails

import android.os.Parcelable
import com.airbnb.mvrx.*
import com.aradipatrik.singlemoduleboilerplate.MvRxViewModel
import com.aradipatrik.singlemoduleboilerplate.data.RepoRepository
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoDetailsArgs(val id: Int) : Parcelable

data class RepoDetailsState(
    val repoId: Int,
    val repo: Async<LocalRepo> = Uninitialized
) : MvRxState {
    @Suppress("unused")
    constructor(args: RepoDetailsArgs) : this(args.id)
}

class RepoDetailsViewModel @AssistedInject constructor(
    @Assisted initialState: RepoDetailsState,
    private val repository: RepoRepository
) : MvRxViewModel<RepoDetailsState>(initialState) {
    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: RepoDetailsState): RepoDetailsViewModel
    }

    companion object : MvRxViewModelFactory<RepoDetailsViewModel, RepoDetailsState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: RepoDetailsState
        ): RepoDetailsViewModel? {
            val fragment =
                (viewModelContext as FragmentViewModelContext).fragment<RepoDetailsFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }

    init {
        fetchDetails()
    }

    private fun fetchDetails() = withState { state ->
        repository.getRepoFromLocal(state.repoId)
            .subscribeOn(Schedulers.io())
            .execute { copy(repo = it) }
    }

    fun setFavorite(favorite: Boolean) = withState { state ->
        state.repo()?.let {
            repository.setFavorite(it, favorite)
                .subscribeOn(Schedulers.io())
                .execute { this }
        }
    }
}