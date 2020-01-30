package com.aradipatrik.singlemoduleboilerplate.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface FooService {

    @GET("foo")
    fun getFoos(): Single<List<RemoteFoo>>

    @GET("foo/{id}")
    fun getFoo(@Path("id") id: Long): Single<RemoteFoo>
}