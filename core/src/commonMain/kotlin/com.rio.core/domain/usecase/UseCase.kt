package com.rio.core.domain.usecase

interface UseCase<in R, T> {
    suspend fun execute(request: R?): T
}