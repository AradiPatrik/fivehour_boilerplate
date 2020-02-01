package com.aradipatrik.singlemoduleboilerplate.feature.listrepos

import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo

sealed class ListRepoViewEvent {
    data class ItemClick(val repo: LocalRepo): ListRepoViewEvent()
    data class LikeClick(val repo: LocalRepo): ListRepoViewEvent()
    object DayNightMenuClick: ListRepoViewEvent()
}