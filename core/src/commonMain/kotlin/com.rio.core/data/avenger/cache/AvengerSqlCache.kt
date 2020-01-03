package com.rio.core.data.avenger.cache

import com.rio.core.data.avenger.entity.AvengerData
import com.rio.core.data.common.Cache
import com.rio.core.data.cache.DatabaseHelper

class AvengerSqlCache(private val db: DatabaseHelper) :
    Cache<AvengerData> {
    override fun insert(model: AvengerData) {
        db.database.avengerQueries.insert(
            model.title,
            model.imdbID,
            model.year,
            model.type,
            model.poster
        )
    }

    override fun bulkInsert(list: List<AvengerData>) {
        db.database.transaction {
            list.forEach {
                insert(it)
            }
        }
    }

    override fun clear() {
        db.database.avengerQueries.deleteAll()
    }

    override fun selectAll(): List<AvengerData> {
        val records = db.database.avengerQueries.selectAll().executeAsList()

        return records.map {
            AvengerData(
                it.title,
                it.imdbID,
                it.year,
                it.type,
                it.poster
            )
        }
    }
}