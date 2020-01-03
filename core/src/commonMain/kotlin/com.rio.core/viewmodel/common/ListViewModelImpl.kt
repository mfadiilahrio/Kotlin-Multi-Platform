package com.rio.core.viewmodel.common

import com.badoo.reaktive.observable.*
import com.badoo.reaktive.subject.publish.publishSubject
import com.rio.core.data.common.Mapper
import com.rio.core.domain.usecase.UseCase
import com.rio.core.helper.singleFromCoroutine

class ListViewModelImpl<R, T, E>(
    useCase: UseCase<R, List<T>>,
    mapper: Mapper<List<T>, List<E>>?
): ListViewModel<R, E>,
    ListViewModelInput<R>,
    ListViewModelOutput<R, E> {
    override val inputs: ListViewModelInput<R> = this
    override val outputs: ListViewModelOutput<R, E> = this

    override val loading: Observable<Boolean>
    override val result: Observable<List<E>>

    private val mListProperty = publishSubject<R>()
    private val mLoadMoreProperty = publishSubject<R>()

    init {
        val loadingProperty = publishSubject<Boolean>()

        val items = mutableListOf<E>()

        var clearItems = false

        loading = loadingProperty

        val initialRequest = mListProperty
            .doOnBeforeNext { loadingProperty.onNext(true) }
            .flatMapSingle { request ->
                singleFromCoroutine { useCase.execute(request) }
            }
            .doOnBeforeNext {
                loadingProperty.onNext(false)
                clearItems = true
            }

        val nextRequest = mLoadMoreProperty
            .doOnBeforeNext { loadingProperty.onNext(true) }
            .flatMapSingle { request ->
                singleFromCoroutine { useCase.execute(request) }
            }
            .doOnBeforeNext {
                loadingProperty.onNext(false)
                clearItems = false
            }

        result = merge(initialRequest, nextRequest).map { response ->
            if (clearItems) {
                items.clear()
            }

            @Suppress("UNCHECKED_CAST")
            val list = mapper?.transform(response) ?: response as List<E>

            items.addAll(list)

            items
        }

    }

    override fun get(request: R) {
        mListProperty.onNext(request)
    }

    override fun loadMore(request: R) {
        mLoadMoreProperty.onNext(request)
    }

}