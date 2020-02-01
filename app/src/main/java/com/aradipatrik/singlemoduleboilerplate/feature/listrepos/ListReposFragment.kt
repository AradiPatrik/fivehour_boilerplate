package com.aradipatrik.singlemoduleboilerplate.feature.listrepos

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import android.view.*
import androidx.annotation.IdRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.mvrx.*
import com.aradipatrik.singlemoduleboilerplate.MainViewModel
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.feature.listrepos.ListRepoViewEvent.DayNightMenuClick
import com.aradipatrik.singlemoduleboilerplate.feature.repodetails.RepoDetailsArgs
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_list_repos.*
import javax.inject.Inject

class ListReposFragment : BaseMvRxFragment(R.layout.fragment_list_repos) {
    @Inject
    lateinit var viewModelFactory: ListReposViewModel.Factory
    private val viewModel: ListReposViewModel by fragmentViewModel()
    private val mainViewModel: MainViewModel by activityViewModel()

    @Inject
    lateinit var adapter: RepoAdapter

    private val eventsDisposable = CompositeDisposable()
    private val menuDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        menuDisposable += menu.findItem(R.id.menu_daynight)
            .clicks()
            .map { DayNightMenuClick }
            .subscribe(::handleEvent)
    }

    override fun onDestroyOptionsMenu() {
        menuDisposable.clear()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_repo.adapter = adapter
        recycler_view_repo.layoutManager = LinearLayoutManager(context)
    }

    override fun onResume() {
        super.onResume()
        eventsDisposable += adapter.events.subscribe(::handleEvent)
    }

    private fun handleEvent(event: ListRepoViewEvent) = when (event) {
        is ListRepoViewEvent.ItemClick -> navigateTo(
            R.id.action_fragment_list_repos_to_repoDetailsFragment,
            RepoDetailsArgs(id = event.repo.id)
        )
        is ListRepoViewEvent.LikeClick -> viewModel.toggleFavorite(event.repo)
        DayNightMenuClick -> mainViewModel.toggleDarkMode()
    }

    override fun onPause() {
        super.onPause()
        eventsDisposable.clear()
    }

    override fun invalidate() = withState(viewModel) { state ->
        adapter.submitList(state.repoList)
    }

    private fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }
}
