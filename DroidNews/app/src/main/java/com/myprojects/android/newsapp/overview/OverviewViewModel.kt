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

import android.app.Application
import androidx.lifecycle.*
import com.myprojects.android.newsapp.database.getDatabase
import com.myprojects.android.newsapp.network.Article
import com.myprojects.android.newsapp.repository.ArticlesRepository
import kotlinx.coroutines.launch


/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */

//enum class NewsApiStatus { LOADING, ERROR, DONE }

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val articlesRepository = ArticlesRepository(database)

    init {
        viewModelScope.launch {
            articlesRepository.refreshArticles("android")
        }
    }

    val properties = articlesRepository.articles

    //Navigation
    private val _navigateToSelectedProperty = MutableLiveData<Article>()

    val navigateToSelectedProperty: LiveData<Article>
        get() = _navigateToSelectedProperty

    override fun onCleared() {
        super.onCleared()
    }

    fun displayPropertyDetails(article: Article) {
        _navigateToSelectedProperty.value = article

    }

    fun displayPropertyDetailsComplete() { _navigateToSelectedProperty.value = null }

    fun updateFilter(filter: String) {
        viewModelScope.launch {
            articlesRepository.refreshArticles(filter)
        }
    }

    /**
     * Factory for constructing OverViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OverviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


//    init {
//        getNewsProperties("android")
//    }
//
//    private val _status = MutableLiveData<NewsApiStatus>()
//
//    // The external immutable LiveData for the request status String
//    val status: LiveData<NewsApiStatus>
//        get() = _status
//
//    private var viewModelJob = Job()
//    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )
//
//    private fun getNewsProperties(filter: String) {
//        coroutineScope.launch {
//            var getPropertiesDeferred = NewsApi.retrofitService.getProperties(filter)
//            try {
//                _status.value = NewsApiStatus.LOADING
//
//                val listResult =  getPropertiesDeferred.await()
//                _status.value = NewsApiStatus.DONE
//                _properties.value = listResult.articles
//            } catch (e: Exception) {
//
//                _status.value = NewsApiStatus.ERROR
//                Log.d("myTag", "me" + e)
//                _properties.value = ArrayList()
//            }
//        }
//
//    }


}
