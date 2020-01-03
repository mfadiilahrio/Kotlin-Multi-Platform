package com.rio.core.data.common

interface Mapper<in T, out E> {
    fun transform(response: T): E
}