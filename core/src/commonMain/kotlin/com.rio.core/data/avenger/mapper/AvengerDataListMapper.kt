package com.rio.core.data.avenger.mapper

import com.rio.core.data.common.Mapper
import com.rio.core.data.avenger.entity.AvengerData
import com.rio.core.data.avenger.response.AvengersResponse

class AvengerDataListMapper:
    Mapper<AvengersResponse, List<AvengerData>> {

    override fun transform(response: AvengersResponse): List<AvengerData> {
        return response.items.map {
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