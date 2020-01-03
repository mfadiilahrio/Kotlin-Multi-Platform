package com.rio.core.data.common

interface Cache<T> {
    fun insert(model: T)
    fun bulkInsert(list: List<T>)
    fun clear()
    fun selectAll(): List<T>
}