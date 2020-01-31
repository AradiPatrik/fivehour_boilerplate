package com.aradipatrik.singlemoduleboilerplate.feature.listfoos

import android.content.Context
import android.os.Bundle
import android.view.View
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.aradipatrik.singlemoduleboilerplate.R
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_listfoos.*
import javax.inject.Inject

class ListReposFragment : BaseMvRxFragment(R.layout.fragment_listfoos) {
    @Inject
    lateinit var viewModelFactory: ListReposViewModel.Factory
    private val viewModel: ListReposViewModel by fragmentViewModel()

    @Inject
    lateinit var adapter: RepoAdapter

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view_foo.adapter = adapter
    }

    override fun invalidate() = withState(viewModel) { state ->
        adapter.submitList(state.repoList)
    }
}