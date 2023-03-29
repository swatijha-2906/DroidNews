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

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {
    @Query("select * from DatabaseArticle") fun getArticles(): LiveData<List<DatabaseArticle>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg articles: DatabaseArticle)
    @Query("DELETE FROM DatabaseArticle")
    fun clear()
}

@Database(entities = [DatabaseArticle::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract val articleDao: ArticleDao
}

private lateinit var INSTANCE: ArticleDatabase
fun getDatabase(context: Context): ArticleDatabase {
    synchronized(ArticleDatabase::class.java){
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ArticleDatabase::class.java,
                "articles").build()
        }
    }
    return INSTANCE
}