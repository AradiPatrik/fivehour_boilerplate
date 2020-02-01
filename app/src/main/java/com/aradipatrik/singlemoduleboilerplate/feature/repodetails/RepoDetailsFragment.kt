package com.aradipatrik.singlemoduleboilerplate.feature.repodetails

import android.content.Context
import android.os.Bundle
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.feature.repodetails.RepoDetailsViewEvent.FavoriteClicked
import com.jakewharton.rxbinding3.widget.checkedChanges
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.fragment_repodetails.*
import javax.inject.Inject

class RepoDetailsFragment : BaseMvRxFragment(R.layout.fragment_repodetails) {

    @Inject
    lateinit var viewModelFactory: RepoDetailsViewModel.Factory
    private val viewModel: RepoDetailsViewModel by fragmentViewModel()

    private val events: Observable<RepoDetailsViewEvent> get() = like_switch
        .checkedChanges()
        .skipInitialValue()
        .map { FavoriteClicked(it) }

    private val eventsDisposable = CompositeDisposable()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        eventsDisposable += events.subscribe(::handleEvent)
    }

    private fun handleEvent(event: RepoDetailsViewEvent) = when(event) {
        is FavoriteClicked -> viewModel.setFavorite(event.newFavoriteState)
    }

    override fun onPause() {
        super.onPause()
        eventsDisposable.clear()
    }

    override fun invalidate() = withState(viewModel) { state ->
        repo_name.text = state.repo()?.name ?: ""
        loading_indicator.isVisible = state.repo is Loading
        like_switch.isChecked = state.repo()?.isFavorited ?: false
    }
}
