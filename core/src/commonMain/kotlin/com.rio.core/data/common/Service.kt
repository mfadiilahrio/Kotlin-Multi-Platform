package com.rio.core.data.common

interface Service<in R, T> {
    suspend fun execute(request: R?): T
}