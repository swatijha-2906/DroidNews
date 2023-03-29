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

package com.myprojects.android.newsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.myprojects.android.newsapp.database.ArticleDatabase
import com.myprojects.android.newsapp.database.DatabaseArticle
import com.myprojects.android.newsapp.database.asDomainModel
import com.myprojects.android.newsapp.network.Article
import com.myprojects.android.newsapp.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class ArticlesRepository(private val database: ArticleDatabase) {

    val articles: LiveData<List<Article>> = Transformations.map(database.articleDao.getArticles()) {
        it.asDomainModel()
    }

    suspend fun refreshArticles(filter: String) {
        withContext(Dispatchers.IO) {
        try{
            var getPropertiesDeferred = NewsApi.retrofitService.getProperties(filter)
            val listResult =  getPropertiesDeferred.await()
            Log.d("myTag", listResult.status)
            Log.d("myTag", listResult.totalResults.toString())
            database.articleDao.clear()
            database.articleDao.insertAll(*listResult.articles.asDatabaseModel())
        }catch (e: Exception){
            Log.d("MyTag", "network not connected")
        }

        }
    }
}


fun List<Article>.asDatabaseModel(): Array<DatabaseArticle> {
    return map {
            DatabaseArticle(
                title= it.title.toString(),
                description = it.description,
                content = it.content,
                urlToImage = it.urlToImage,
                url=it.url,
                author = it.author)

    }.toTypedArray()

}




