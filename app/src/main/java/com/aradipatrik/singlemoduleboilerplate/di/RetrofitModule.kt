package com.aradipatrik.singlemoduleboilerplate.di

import com.aradipatrik.singlemoduleboilerplate.data.remote.RepoService
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {
    @JvmStatic
    @Provides
    @Singleton
    fun providerRepoService(): RepoService = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
        .create(RepoService::class.java)
}