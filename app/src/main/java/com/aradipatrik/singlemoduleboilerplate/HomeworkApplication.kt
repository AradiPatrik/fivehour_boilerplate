package com.aradipatrik.singlemoduleboilerplate

import com.aradipatrik.singlemoduleboilerplate.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class HomeworkApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.factory().create(this).also { it.inject(this) }
}