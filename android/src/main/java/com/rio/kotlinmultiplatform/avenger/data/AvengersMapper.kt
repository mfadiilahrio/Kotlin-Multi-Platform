package com.rio.kotlinmultiplatform.avenger.data

import com.rio.core.data.common.Mapper
import com.rio.core.data.avenger.entity.AvengerData

class AvengersMapper: Mapper<List<AvengerData>, List<Avenger>> {

    override fun transform(response: List<AvengerData>): List<Avenger> {
        return response.map {
            Avenger(
                it.title,
                it.imdbID,
                it.year,
                it.type,
                it.poster
            )
        }
    }
}