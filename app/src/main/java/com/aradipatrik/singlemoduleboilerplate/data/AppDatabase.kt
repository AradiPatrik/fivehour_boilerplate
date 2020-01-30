package com.aradipatrik.singlemoduleboilerplate.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalFoo
import com.aradipatrik.singlemoduleboilerplate.data.local.FooDao
import javax.inject.Singleton

@Singleton
@Database(entities = [LocalFoo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fooDao(): FooDao
}