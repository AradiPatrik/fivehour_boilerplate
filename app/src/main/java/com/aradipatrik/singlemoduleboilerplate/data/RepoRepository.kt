package com.aradipatrik.singlemoduleboilerplate.data

import com.aradipatrik.singlemoduleboilerplate.data.local.RepoDao
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.data.remote.RepoService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val repoRemote: RepoService,
    private val repoLocal: RepoDao
) {
    fun getAllRepos(): Observable<List<LocalRepo>> =
        repoLocal
            .getRepos()
            .toObservable()
            .distinctUntilChanged()

    fun getFavoriteRepos(): Observable<List<LocalRepo>> =
        repoLocal
            .getFavoriteRepos()
            .toObservable()
            .distinctUntilChanged()

    fun syncRepos(): Completable =
        repoRemote
            .getRepos("aradipatrik")
            .flatMapCompletable {
                repoLocal.insertAll(it.map(LocalRepo.Companion::fromRemote))
            }
}
