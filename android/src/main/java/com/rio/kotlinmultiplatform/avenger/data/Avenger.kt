package com.rio.kotlinmultiplatform.avenger.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Avenger(
    val title: String,
    val imdbID: String,
    val year: String,
    val type: String,
    val poster: String
): Parcelable