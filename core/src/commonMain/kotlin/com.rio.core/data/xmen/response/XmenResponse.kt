package com.rio.core.data.xmen.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XmenResponse(
    @SerialName("Title") val title: String,
    @SerialName("imdbID") val imdbID: String,
    @SerialName("Year") val year: String,
    @SerialName("Type") val type: String,
    @SerialName("Poster") val poster: String
)