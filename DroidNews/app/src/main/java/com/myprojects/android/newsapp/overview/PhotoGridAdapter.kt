/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.myprojects.android.newsapp.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myprojects.android.newsapp.databinding.GridViewItemBinding
import com.myprojects.android.newsapp.network.Article

class PhotoGridAdapter(val onClickListener: OnClickListener) : ListAdapter<Article, PhotoGridAdapter.ArticleViewHolder>(DiffCallback) {



    class ArticleViewHolder(private var binding: GridViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.property = article
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title.equals(newItem.title)
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            PhotoGridAdapter.ArticleViewHolder {
        return ArticleViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(article)
        }
        holder.bind(article)
    }
    class OnClickListener(val clickListener: (marsProperty: Article) -> Unit) {
        fun onClick(article:Article) = clickListener(article)
    }


}
