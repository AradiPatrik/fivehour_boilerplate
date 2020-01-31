package com.aradipatrik.singlemoduleboilerplate.feature.favoritefoos

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.feature.favoritefoos.FavoriteRepoAdapter.ViewHolder
import com.aradipatrik.singlemoduleboilerplate.feature.listfoos.LocalRepoItemCallback
import com.aradipatrik.singlemoduleboilerplate.util.inflate
import kotlinx.android.synthetic.main.list_item_favorite_foo.view.*

class FavoriteRepoAdapter : ListAdapter<LocalRepo, ViewHolder>(LocalRepoItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        parent.inflate(R.layout.list_item_favorite_foo)
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