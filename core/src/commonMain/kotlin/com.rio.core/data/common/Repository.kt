package com.rio.core.data.common

interface Repository<in R, T> {
    suspend fun get(request: R?): T
}