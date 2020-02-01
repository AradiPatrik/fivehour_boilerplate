package com.aradipatrik.singlemoduleboilerplate.data

import com.aradipatrik.singlemoduleboilerplate.data.local.RepoDao
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.data.remote.RepoService
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val remoteRepoDataStore: RepoService,
    private val localRepoDataStore: RepoDao
) {
    fun getAllRepos(): Observable<List<LocalRepo>> =
        localRepoDataStore
            .getRepos()
            .toObservable()
            .distinctUntilChanged()

    fun getFavoriteRepos(): Observable<List<LocalRepo>> =
        localRepoDataStore
            .getFavoriteRepos()
            .toObservable()
            .distinctUntilChanged()

    fun setFavorite(repo: LocalRepo, isFavorite: Boolean) =
        localRepoDataStore.updateRepos(repo.copy(isFavorited = isFavorite))

    fun getRepoFromLocal(id: Int) =
        localRepoDataStore.getRepo(id)
            .toObservable()

    fun syncRepos(): Completable =
        remoteRepoDataStore
            .getRepos("aradipatrik")
            .flatMapCompletable {
                localRepoDataStore.insertAll(it.map(LocalRepo.Companion::fromRemote))
            }
}
