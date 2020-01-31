package com.aradipatrik.singlemoduleboilerplate.di

import android.content.Context
import com.aradipatrik.singlemoduleboilerplate.HomeworkApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppAssistedModule::class,
        RoomModule::class,
        RetrofitModule::class,
        ActivityFragmentBuilder::class
    ]
)
interface ApplicationComponent : AndroidInjector<DaggerApplication> {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context
        ): ApplicationComponent
    }

    fun inject(app: HomeworkApplication)

    override fun inject(instance: DaggerApplication?)
}