package com.rio.core.data.xmen.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class XmensResponse(
    @SerialName("Search")
    val items: List<XmenResponse>)