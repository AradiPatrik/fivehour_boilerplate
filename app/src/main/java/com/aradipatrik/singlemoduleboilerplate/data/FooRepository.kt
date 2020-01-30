package com.aradipatrik.singlemoduleboilerplate.data

import com.aradipatrik.singlemoduleboilerplate.data.local.FooDao
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalFoo
import com.aradipatrik.singlemoduleboilerplate.data.remote.FooService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FooRepository @Inject constructor(
    private val fooRemote: FooService,
    private val fooLocal: FooDao
) {
    fun getAllFoos(): Observable<List<LocalFoo>> =
        fooLocal
            .getFoos()
            .toObservable()
            .distinctUntilChanged()

    fun getFavoriteFoos(): Observable<List<LocalFoo>> =
        fooLocal
            .getFavoriteFoos()
            .toObservable()
            .distinctUntilChanged()

    fun syncFoos(): Completable =
        fooRemote
            .getFoos()
            .flatMapCompletable {
                fooLocal.insertAll(it.map(LocalFoo.Companion::fromRemote))
            }
}