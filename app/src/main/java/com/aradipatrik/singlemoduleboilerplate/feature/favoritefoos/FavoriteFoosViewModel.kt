package com.aradipatrik.singlemoduleboilerplate.feature.favoritefoos

import com.airbnb.mvrx.*
import com.aradipatrik.singlemoduleboilerplate.MvRxViewModel
import com.aradipatrik.singlemoduleboilerplate.data.FooRepository
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalFoo
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers

data class FavoriteFoosState(
    val favoriteFoos: List<LocalFoo> = emptyList(),
    val favoriteFoosRequest: Async<List<LocalFoo>> = Uninitialized,
    val syncRequest: Async<Unit> = Uninitialized
): MvRxState

class FavoriteFoosViewModel @AssistedInject constructor(
    @Assisted state: FavoriteFoosState,
    private val fooRepository: FooRepository
) : MvRxViewModel<FavoriteFoosState>(state) {

    @AssistedInject.Factory
    interface Factory {
        fun create(state: FavoriteFoosState): FavoriteFoosViewModel
    }

    companion object: MvRxViewModelFactory<FavoriteFoosViewModel, FavoriteFoosState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: FavoriteFoosState
        ): FavoriteFoosViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<FavoriteFoosFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }

    fun fetchFavorites() {
        fooRepository.getFavoriteFoos()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(favoriteFoos = it() ?: favoriteFoos, favoriteFoosRequest = it)
            }

        fooRepository.syncFoos()
            .subscribeOn(Schedulers.io())
            .execute {
                copy(syncRequest = it)
            }
    }
}