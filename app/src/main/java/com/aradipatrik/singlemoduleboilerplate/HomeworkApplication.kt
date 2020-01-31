package com.aradipatrik.singlemoduleboilerplate

import android.app.Application
import com.aradipatrik.singlemoduleboilerplate.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class HomeworkApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any?>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.factory().create(this)
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any?>? {
        return dispatchingAndroidInjector
    }

}