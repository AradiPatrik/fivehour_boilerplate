package com.aradipatrik.singlemoduleboilerplate.feature.favoritefoos

import android.content.Context
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FavoriteFoosFragment : BaseMvRxFragment() {
    @Inject
    lateinit var viewModelFactory: FavoriteFoosViewModel.Factory
    private val viewModel: FavoriteFoosViewModel by fragmentViewModel()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun invalidate() = withState(viewModel) { state ->
    }

}