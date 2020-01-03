package com.rio.kotlinmultiplatform.avenger.dependency

import androidx.recyclerview.widget.RecyclerView
import com.rio.core.data.avenger.cache.AvengerSqlCache
import com.rio.core.data.avenger.cloudservice.AvengersCloudService
import com.rio.core.data.avenger.mapper.AvengerDataListMapper
import com.rio.core.data.avenger.repository.AvangerDataListRepositoryImpl
import com.rio.core.domain.usecase.ListUseCaseImpl
import com.rio.core.viewmodel.common.ListViewModel
import com.rio.core.viewmodel.common.ListViewModelImpl
import com.rio.kotlinmultiplatform.common.AdapterClickListener
import com.rio.kotlinmultiplatform.common.RecyclerViewAdapter
import com.rio.kotlinmultiplatform.avenger.data.Avenger
import com.rio.kotlinmultiplatform.avenger.data.AvengersMapper
import com.rio.kotlinmultiplatform.avenger.view.AvengerAdapter
import com.rio.kotlinmultiplatform.avenger.view.AvengerViewHolderFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val avengerModule = module {

    factory<ListViewModel<String, Avenger>>(named("avengersViewModel")) {
        val domainMapper = AvengerDataListMapper()

        val service = AvengersCloudService(
            get(named("key")),
            get(named("hosturl")),
            domainMapper
        )

        val cache = AvengerSqlCache(get())

        val repository =
            AvangerDataListRepositoryImpl(
                service,
                cache
            )

        val useCase = ListUseCaseImpl(repository)

        val mapper = AvengersMapper()

        ListViewModelImpl(useCase, mapper)
    }

    factory(named("avengerViewHolder")) {
        mapOf(0 to AvengerViewHolderFactory())
    }

    factory<RecyclerViewAdapter<AdapterClickListener<Avenger>, Avenger, RecyclerView.ViewHolder>>(named("avengerListAdapter")) {
        AvengerAdapter(get(named("avengerViewHolder")))
    }

}