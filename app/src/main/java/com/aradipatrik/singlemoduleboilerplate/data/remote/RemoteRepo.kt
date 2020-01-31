package com.aradipatrik.singlemoduleboilerplate.data.remote

import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRepo(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String
) {
    companion object {
        fun fromLocal(local: LocalRepo) = RemoteRepo(local.id, local.name)
    }
}
