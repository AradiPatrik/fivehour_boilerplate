package com.aradipatrik.singlemoduleboilerplate.feature.listfoos

import com.airbnb.mvrx.*
import com.aradipatrik.singlemoduleboilerplate.MvRxViewModel
import com.aradipatrik.singlemoduleboilerplate.data.FooRepository
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalFoo
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import io.reactivex.schedulers.Schedulers

data class ListFoosState(
    val fooList: List<LocalFoo> = emptyList(),
    val fooListRequest: Async<List<LocalFoo>> = Uninitialized,
    val syncRequest: Async<Unit> = Uninitialized
) : MvRxState

class ListFoosViewModel @AssistedInject constructor(
    @Assisted state: ListFoosState,
    private val fooRepository: FooRepository
) : MvRxViewModel<ListFoosState>(state) {

    @AssistedInject.Factory
    interface Factory {
        fun create(state: ListFoosState): ListFoosViewModel
    }

    companion object : MvRxViewModelFactory<ListFoosViewModel, ListFoosState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: ListFoosState
        ): ListFoosViewModel? {
            val fragment = (viewModelContext as FragmentViewModelContext).fragment<ListFoosFragment>()
            return fragment.viewModelFactory.create(state)
        }
    }

    init {
        fetchFoos()
    }

    fun fetchFoos() {
        withState {
            fooRepository
                .getAllFoos()
                .subscribeOn(Schedulers.io())
                .execute {
                    copy(
                        fooList = it() ?: fooList,
                        fooListRequest = it
                    )
                }

            fooRepository.syncFoos()
                .subscribeOn(Schedulers.io())
                .execute {
                    copy(
                        syncRequest = it
                    )
                }
        }
    }

}