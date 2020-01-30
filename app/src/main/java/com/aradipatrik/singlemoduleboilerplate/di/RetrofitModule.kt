package com.aradipatrik.singlemoduleboilerplate.di

import com.aradipatrik.singlemoduleboilerplate.data.remote.FooService
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
    fun provideFooService(): FooService = Retrofit.Builder()
        .baseUrl("https://foo.com/apiv1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
        .create(FooService::class.java)
}