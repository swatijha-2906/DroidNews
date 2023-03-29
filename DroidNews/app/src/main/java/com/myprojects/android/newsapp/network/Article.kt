package com.myprojects.android.newsapp.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Article(

    val title: String?,
    val description: String?,
    val content: String?,
    val urlToImage: String?,
    val url: String?,
    val author: String?
): Parcelable




