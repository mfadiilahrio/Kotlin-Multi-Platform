package com.rio.kotlinmultiplatform

import com.rio.core.Constants
import com.rio.core.data.cache.DatabaseHelper
import com.rio.core.sql.MoviesDatabase
import com.rio.core.viewmodel.ViewModelBinding
import com.squareup.sqldelight.android.AndroidSqliteDriver
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("key")) { "5e360c9" }

    single(named("hosturl")) { "https://www.omdbapi.com/" }

    single { ViewModelBinding() }

    single {
        val driver = AndroidSqliteDriver(
            schema = MoviesDatabase.Schema,
            context = androidApplication().applicationContext,
            name = Constants.db.DB_NAME
        )

        DatabaseHelper(Constants.db.DB_NAME, driver)
    }
}