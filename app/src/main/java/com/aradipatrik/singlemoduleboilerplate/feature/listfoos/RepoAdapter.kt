package com.aradipatrik.singlemoduleboilerplate.feature.listfoos

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalRepo
import com.aradipatrik.singlemoduleboilerplate.feature.listfoos.RepoAdapter.ViewHolder
import com.aradipatrik.singlemoduleboilerplate.util.inflate
import kotlinx.android.synthetic.main.list_item_foo.view.*
import javax.inject.Inject

object LocalRepoItemCallback : DiffUtil.ItemCallback<LocalRepo>() {
    override fun areItemsTheSame(oldItem: LocalRepo, newItem: LocalRepo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LocalRepo, newItem: LocalRepo) = oldItem == newItem
}

class RepoAdapter @Inject constructor() : ListAdapter<LocalRepo, ViewHolder>(LocalRepoItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_foo))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.foo_text
        private val imageView: ImageView = view.heart_icon

        fun bind(repo: LocalRepo) {
            textView.text = repo.name
            imageView.setImageResource(
                if (repo.isFavorited) {
                        R.drawable.ic_favorite_black
                    }
                else {
                    R.drawable.ic_favorite_border_black
                }
            )
        }
    }
}
