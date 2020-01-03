package com.rio.core.data.avenger.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AvengersResponse(
    @SerialName("Search")
    val items: List<AvengerResponse>)