package com.aradipatrik.singlemoduleboilerplate.di

import com.aradipatrik.singlemoduleboilerplate.MainActivity
import com.aradipatrik.singlemoduleboilerplate.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityFragmentBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindMainFragment(): MainFragment
}
