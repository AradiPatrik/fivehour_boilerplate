package com.aradipatrik.singlemoduleboilerplate.feature.listrepos

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.feature.listrepos.RepoAdapter.ViewHolder
import com.aradipatrik.singlemoduleboilerplate.util.inflate
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.list_item_repo.view.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

object LocalRepoItemCallback : DiffUtil.ItemCallback<LocalRepo>() {
    override fun areItemsTheSame(oldItem: LocalRepo, newItem: LocalRepo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LocalRepo, newItem: LocalRepo) = oldItem == newItem
}

class RepoAdapter @Inject constructor() :
    ListAdapter<LocalRepo, ViewHolder>(LocalRepoItemCallback) {
    companion object {
        const val MIN_TIME_BETWEEN_CLICK_EVENTS_MILLISECONDS = 500L
    }

    val events = PublishSubject.create<ListRepoViewEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_repo)).also { it.events.subscribe(events) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        events.onComplete()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView = view.repo_name
        private val imageView = view.heart_icon
        private val container = view.list_item_repo_container

        private lateinit var boundItem: LocalRepo

        val events: Observable<ListRepoViewEvent> get() = Observable.merge(
            container.clicks().map { ListRepoViewEvent.ItemClick(boundItem) },
            imageView.clicks().map { ListRepoViewEvent.LikeClick(boundItem) }
        ).throttleFirst(MIN_TIME_BETWEEN_CLICK_EVENTS_MILLISECONDS, TimeUnit.MILLISECONDS)

        fun bind(repo: LocalRepo) {
            boundItem = repo
            textView.text = repo.name
            imageView.setImageResource(
                if (repo.isFavorited) {
                    R.drawable.ic_favorite_black
                } else {
                    R.drawable.ic_favorite_border_black
                }
            )
        }
    }
}
