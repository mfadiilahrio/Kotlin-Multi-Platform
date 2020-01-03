package com.rio.core.data.xmen.mapper

import com.rio.core.data.common.Mapper
import com.rio.core.data.xmen.entity.XmenData
import com.rio.core.data.xmen.response.XmenResponse
import com.rio.core.data.xmen.response.XmensResponse

class XmenDataListMapper:
    Mapper<XmensResponse, List<XmenData>> {

    override fun transform(response: XmensResponse): List<XmenData> {
        return response.items.map {
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