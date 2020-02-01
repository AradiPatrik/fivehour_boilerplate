package com.aradipatrik.singlemoduleboilerplate

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController =
            Navigation.findNavController(view.findViewById(R.id.content_nav_host_fragment))
        bottom_navigation_view.setupWithNavController(navController)
        toolbar.setupWithNavController(navController, AppBarConfiguration(setOf(R.id.fragment_list_favorite_repos, R.id.fragment_list_repos)))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
    }
}