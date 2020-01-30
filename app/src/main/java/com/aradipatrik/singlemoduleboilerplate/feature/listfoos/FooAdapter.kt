package com.aradipatrik.singlemoduleboilerplate.feature.listfoos

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aradipatrik.singlemoduleboilerplate.R
import com.aradipatrik.singlemoduleboilerplate.data.local.LocalFoo
import com.aradipatrik.singlemoduleboilerplate.feature.listfoos.FooAdapter.ViewHolder
import com.aradipatrik.singlemoduleboilerplate.util.inflate
import kotlinx.android.synthetic.main.list_item_foo.view.*
import javax.inject.Inject

object LocalFooItemCallback : DiffUtil.ItemCallback<LocalFoo>() {
    override fun areItemsTheSame(oldItem: LocalFoo, newItem: LocalFoo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: LocalFoo, newItem: LocalFoo) = oldItem == newItem
}

class FooAdapter @Inject constructor() : ListAdapter<LocalFoo, ViewHolder>(LocalFooItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.list_item_foo))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.foo_text
        private val imageView: ImageView = view.heart_icon

        fun bind(foo: LocalFoo) {
            textView.text = foo.displayableAttribute
            imageView.setImageResource(
                if (foo.isFavorited) {
                        R.drawable.ic_favorite_black
                    }
                else {
                    R.drawable.ic_favorite_border_black
                }
            )
        }
    }
}
