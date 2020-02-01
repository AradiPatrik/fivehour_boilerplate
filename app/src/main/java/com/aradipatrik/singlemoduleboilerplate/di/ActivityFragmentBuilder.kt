package com.aradipatrik.singlemoduleboilerplate.di

import com.aradipatrik.singlemoduleboilerplate.MainActivity
import com.aradipatrik.singlemoduleboilerplate.MainFragment
import com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos.FavoriteReposFragment
import com.aradipatrik.singlemoduleboilerplate.feature.listrepos.ListReposFragment
import com.aradipatrik.singlemoduleboilerplate.feature.repodetails.RepoDetailsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun bindListReposFragment(): ListReposFragment

    @ContributesAndroidInjector
    abstract fun bindFavoritesFragment(): FavoriteReposFragment

    @ContributesAndroidInjector
    abstract fun bindRepoDetailsFragment(): RepoDetailsFragment
}
