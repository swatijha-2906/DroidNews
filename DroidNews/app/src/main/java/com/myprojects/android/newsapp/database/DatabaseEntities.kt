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

package com.myprojects.android.newsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myprojects.android.newsapp.network.Article

@Entity
data class DatabaseArticle constructor(

    @PrimaryKey
    val title: String,
    val description: String?,
    val content: String?,
    val urlToImage: String?,
    val url: String?,
    val author: String?
    )


//fun DatabaseArticle.asDomainModel(): DatabaseArticle {
//    return map{Article (title = it.title, description = it.description, content = it.content, urlToImage = it.urlToImage,
//        url= it.url, author= it.author)}
//        }
//
//    }

fun List<DatabaseArticle>.asDomainModel(): List<Article> {
    return map {
        Article(title = it.title, description = it.description, content = it.content, urlToImage = it.urlToImage,
       url= it.url, author= it.author)
    }
}
