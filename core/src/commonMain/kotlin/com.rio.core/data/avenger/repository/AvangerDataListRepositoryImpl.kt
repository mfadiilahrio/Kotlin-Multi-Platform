package com.rio.core.data.avenger.repository

import com.rio.core.data.common.Cache
import com.rio.core.data.common.Repository
import com.rio.core.data.common.Service
import com.rio.core.data.avenger.entity.AvengerData

class AvangerDataListRepositoryImpl<R>(
    private val service: Service<R, List<AvengerData>>,
    private val cache: Cache<AvengerData>
): Repository<R, List<AvengerData>> {

    override suspend fun get(request: R?): List<AvengerData> {
        val cachedList = cache.selectAll()

        return if (cachedList.isNotEmpty()) {
            cachedList
        } else {
            val list = service.execute(request)

            cache.clear()
            cache.bulkInsert(list)

            list
        }
    }
}