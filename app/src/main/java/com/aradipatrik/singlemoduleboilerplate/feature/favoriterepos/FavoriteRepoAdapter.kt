package com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos.FavoriteRepoAdapter.ViewHolder
import com.aradipatrik.singlemoduleboilerplate.feature.listrepos.LocalRepoItemCallback
import com.aradipatrik.singlemoduleboilerplate.util.inflate
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.list_item_favorite_repo.view.*
import javax.inject.Inject

class FavoriteRepoAdapter @Inject constructor() : ListAdapter<LocalRepo, ViewHolder>(LocalRepoItemCallback) {
    val events = PublishSubject.create<FavoriteRepoViewEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        parent.inflate(R.layout.list_item_favorite_repo)
    ).also { it.events.subscribe(events) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(currentList[position])

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        events.onComplete()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = itemView.repo_name
        private val container = itemView.list_item_favorite_container

        private lateinit var boundItem: LocalRepo

        val events: Observable<FavoriteRepoViewEvent> get() =
            container.clicks().map { FavoriteRepoViewEvent.FavoriteRepoClick(boundItem) }

        fun bind(localRepo: LocalRepo) {
            boundItem = localRepo
            textView.text = localRepo.name
        }
    }
}