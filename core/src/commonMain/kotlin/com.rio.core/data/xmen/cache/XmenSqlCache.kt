package com.rio.core.data.xmen.cache

import com.rio.core.data.cache.DatabaseHelper
import com.rio.core.data.common.Cache
import com.rio.core.data.xmen.entity.XmenData

class XmenSqlCache(private val db: DatabaseHelper) :
    Cache<XmenData> {
    override fun insert(model: XmenData) {
        db.database.xmenQueries.insert(
            model.title,
            model.imdbID,
            model.year,
            model.type,
            model.poster
        )
    }

    override fun bulkInsert(list: List<XmenData>) {
        db.database.transaction {
            list.forEach {
                insert(it)
            }
        }
    }

    override fun clear() {
        db.database.xmenQueries.deleteAll()
    }

    override fun selectAll(): List<XmenData> {
        val records = db.database.xmenQueries.selectAll().executeAsList()

        return records.map {
            XmenData(
                it.title,
                it.imdbID,
                it.year,
                it.type,
                it.poster
            )
        }
    }
}