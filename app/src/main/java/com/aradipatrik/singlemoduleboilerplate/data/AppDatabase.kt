package com.aradipatrik.singlemoduleboilerplate.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.data.local.RepoDao
import javax.inject.Singleton

@Singleton
@Database(entities = [LocalRepo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}