package com.rio.kotlinmultiplatform.xmen.data

import com.rio.core.data.common.Mapper
import com.rio.core.data.xmen.entity.XmenData

class XmensMapper: Mapper<List<XmenData>, List<Xmen>> {

    override fun transform(response: List<XmenData>): List<Xmen> {
        return response.map {
            Xmen(
                it.title,
                it.imdbID,
                it.year,
                it.type,
                it.poster
            )
        }
    }
}