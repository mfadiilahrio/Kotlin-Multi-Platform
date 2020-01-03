package com.rio.core.domain.usecase

import com.rio.core.data.common.Repository
import com.rio.core.data.avenger.entity.AvengerData

class ListUseCaseImpl<R, T>(
    private val repository: Repository<R, List<T>>
): UseCase<R, List<T>> {

    override suspend fun execute(request: R?): List<T> {
        return repository.get(request)
    }
}