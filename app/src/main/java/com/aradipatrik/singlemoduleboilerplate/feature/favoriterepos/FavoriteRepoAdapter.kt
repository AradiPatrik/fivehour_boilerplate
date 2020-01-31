package com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.feature.favoriterepos.FavoriteRepoAdapter.ViewHolder
import com.aradipatrik.singlemoduleboilerplate.feature.listrepos.LocalRepoItemCallback
import com.aradipatrik.singlemoduleboilerplate.util.inflate
import kotlinx.android.synthetic.main.list_item_favorite_repo.view.*
import javax.inject.Inject

class FavoriteRepoAdapter @Inject constructor() : ListAdapter<LocalRepo, ViewHolder>(LocalRepoItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        parent.inflate(R.layout.list_item_favorite_repo)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(currentList[position])

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = itemView.foo_text

        fun bind(localRepo: LocalRepo) {
            textView.text = localRepo.name
        }
    }
}