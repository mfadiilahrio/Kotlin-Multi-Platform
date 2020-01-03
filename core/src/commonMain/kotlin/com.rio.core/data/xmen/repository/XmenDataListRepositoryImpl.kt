package com.rio.core.data.xmen.repository

import com.rio.core.data.common.Cache
import com.rio.core.data.common.Repository
import com.rio.core.data.common.Service
import com.rio.core.data.xmen.entity.XmenData

class XmenDataListRepositoryImpl<R>(
    private val service: Service<R, List<XmenData>>,
    private val cache: Cache<XmenData>
): Repository<R, List<XmenData>> {

    override suspend fun get(request: R?): List<XmenData> {
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