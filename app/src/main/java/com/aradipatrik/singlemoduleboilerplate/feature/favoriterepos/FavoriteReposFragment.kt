package com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.aradipatrik.singlemoduleboilerplate.R
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_favorite_repos.*
import javax.inject.Inject

class FavoriteReposFragment : BaseMvRxFragment(R.layout.fragment_favorite_repos) {
    @Inject
    lateinit var viewModelFactory: FavoriteReposViewModel.Factory
    private val viewModel: FavoriteReposViewModel by fragmentViewModel()

    @Inject
    lateinit var adapter: FavoriteRepoAdapter

    private val eventsDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_favorite_repo.adapter = adapter
        recycler_view_favorite_repo.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        eventsDisposable += adapter.events.subscribe(::handleEvent)
    }

    private fun handleEvent(event: FavoriteRepoViewEvent) = when(event) {
        is FavoriteRepoViewEvent.FavoriteRepoClick -> viewModel.removeFromFavorites(event.repo)
    }

    override fun onPause() {
        super.onPause()
        eventsDisposable.clear()
    }


    override fun invalidate() = withState(viewModel) { state ->
        adapter.submitList(state.favoriteRepos)
    }

}