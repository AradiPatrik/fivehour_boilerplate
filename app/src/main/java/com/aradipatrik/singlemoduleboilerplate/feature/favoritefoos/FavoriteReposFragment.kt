package com.aradipatrik.singlemoduleboilerplate.feature.favoritefoos

import android.content.Context
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FavoriteReposFragment : BaseMvRxFragment() {
    @Inject
    lateinit var viewModelFactory: FavoriteReposViewModel.Factory
    private val viewModel: FavoriteReposViewModel by fragmentViewModel()

    @Inject
    lateinit var adapter: FavoriteRepoAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun invalidate() = withState(viewModel) { state ->
        adapter.submitList(state.favoriteRepos)
    }

}