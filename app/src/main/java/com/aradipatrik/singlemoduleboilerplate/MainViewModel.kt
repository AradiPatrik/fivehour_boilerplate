package com.aradipatrik.singlemoduleboilerplate

import com.airbnb.mvrx.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

data class MainApplicationState(
    val isDarkModeEnabled: Async<Boolean> = Success(false)
) : MvRxState

class MainViewModel @AssistedInject constructor(
    @Assisted initialState: MainApplicationState
) : MvRxViewModel<MainApplicationState>(initialState) {
    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: MainApplicationState): MainViewModel
    }

    companion object : MvRxViewModelFactory<MainViewModel, MainApplicationState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: MainApplicationState
        ): MainViewModel? {
            val activity =
                (viewModelContext as ActivityViewModelContext).activity<MainActivity>()
            return activity.viewModelFactory.create(state)
        }
    }

    init {
        logStateChanges()
    }

    fun toggleDarkMode() = setState {
        isDarkModeEnabled()?.let {
            copy(isDarkModeEnabled = Success(!isDarkModeEnabled()!!))
        } ?: this
    }
}