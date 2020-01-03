package com.rio.kotlinmultiplatform.common

interface AdapterClickListener<in T> : Listener {

    fun onItemClicked(model: T)
}
