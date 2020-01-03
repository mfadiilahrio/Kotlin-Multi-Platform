package com.rio.kotlinmultiplatform.xmen.dependency

import com.rio.core.data.xmen.cache.XmenSqlCache
import com.rio.core.data.xmen.cloudservice.XmensCloudService
import com.rio.core.data.xmen.mapper.XmenDataListMapper
import com.rio.core.data.xmen.repository.XmenDataListRepositoryImpl
import com.rio.core.domain.usecase.ListUseCaseImpl
import com.rio.core.viewmodel.common.ListViewModel
import com.rio.core.viewmodel.common.ListViewModelImpl
import com.rio.kotlinmultiplatform.common.RecyclerViewAdapter
import com.rio.kotlinmultiplatform.xmen.data.Xmen
import com.rio.kotlinmultiplatform.xmen.data.XmensMapper
import com.rio.kotlinmultiplatform.xmen.view.XmenAdapter
import com.rio.kotlinmultiplatform.xmen.view.XmenViewHolderFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val xmenModule = module {

    factory<ListViewModel<String, Xmen>>(named("xmensViewModel")) {
        val domainMapper = XmenDataListMapper()

        val service = XmensCloudService(
            get(named("key")),
            get(named("hosturl")),
            domainMapper
        )

        val cache = XmenSqlCache(get())

        val repository =
            XmenDataListRepositoryImpl(
                service,
                cache
            )

        val useCase = ListUseCaseImpl(repository)

        val mapper = XmensMapper()

        ListViewModelImpl(useCase, mapper)
    }

    factory(named("xmenViewHolder")) {
        mapOf(0 to XmenViewHolderFactory())
    }

    factory<RecyclerViewAdapter<Nothing, Xmen, *>>(
        named("xmenListAdapter")
    ) {
        XmenAdapter(get(named("xmenViewHolder")))
    }

}