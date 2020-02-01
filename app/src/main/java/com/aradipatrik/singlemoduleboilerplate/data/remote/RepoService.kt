package com.aradipatrik.singlemoduleboilerplate.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface RepoService {
    @GET("users/{username}/repos")
    fun getRepos(@Path("username") userName: String): Single<List<RemoteRepo>>
}
