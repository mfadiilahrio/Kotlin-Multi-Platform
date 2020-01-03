package com.rio.kotlinmultiplatform.xmen.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Xmen(
    val title: String,
    val imdbID: String,
    val year: String,
    val type: String,
    val poster: String
): Parcelable