package com.aradipatrik.singlemoduleboilerplate

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.withState
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : BaseMvRxFragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by activityViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController =
            Navigation.findNavController(view.findViewById(R.id.content_nav_host_fragment))
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        bottom_navigation_view.setupWithNavController(navController)
        toolbar.setupWithNavController(
            navController,
            AppBarConfiguration(setOf(R.id.fragment_list_favorite_repos, R.id.fragment_list_repos))
        )
    }

    override fun invalidate() = withState(viewModel) { applicationState ->
        val currentNightMode = requireContext().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when {
            currentNightMode == Configuration.UI_MODE_NIGHT_NO
                    && (applicationState.isDarkModeEnabled() == true) -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            currentNightMode == Configuration.UI_MODE_NIGHT_YES
                    && (applicationState.isDarkModeEnabled() == false) -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}
