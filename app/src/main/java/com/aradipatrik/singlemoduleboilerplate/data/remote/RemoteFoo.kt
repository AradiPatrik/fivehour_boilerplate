package com.aradipatrik.singlemoduleboilerplate.data.remote

import com.aradipatrik.singlemoduleboilerplate.data.local.LocalFoo
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteFoo(
    val id: Int,
    val displayableAttribute: String
) {
    companion object {
        fun fromLocal(local: LocalFoo) = RemoteFoo(local.id, local.displayableAttribute)
    }
}