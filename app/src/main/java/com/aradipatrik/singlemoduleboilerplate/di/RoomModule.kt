package com.aradipatrik.singlemoduleboilerplate.di

import android.content.Context
import androidx.room.Room
import com.aradipatrik.singlemoduleboilerplate.data.AppDatabase
import com.aradipatrik.singlemoduleboilerplate.data.local.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideAppDatabase(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "repos.db").build()

    @JvmStatic
    @Provides
    @Singleton
    fun provideRepoDao(appDatabase: AppDatabase): RepoDao = appDatabase.repoDao()

}