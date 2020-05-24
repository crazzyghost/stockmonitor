package com.crazzyghost.stockmonitor.ui.search

import com.crazzyghost.stockmonitor.data.DatabaseManager
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    fun presenter(database: DatabaseManager) : SearchContract.Presenter{
        return SearchPresenter(database)
    }
}