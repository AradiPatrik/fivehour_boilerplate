package com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favorite_repos.*
import javax.inject.Inject

class FavoriteReposFragment : BaseMvRxFragment() {
    @Inject
    lateinit var viewModelFactory: FavoriteReposViewModel.Factory
    private val viewModel: FavoriteReposViewModel by fragmentViewModel()

    @Inject
    lateinit var adapter: FavoriteRepoAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_favorite_repo.adapter = adapter
        recycler_view_favorite_repo.layoutManager = LinearLayoutManager(context)
    }

    override fun invalidate() = withState(viewModel) { state ->
        adapter.submitList(state.favoriteRepos)
    }

}