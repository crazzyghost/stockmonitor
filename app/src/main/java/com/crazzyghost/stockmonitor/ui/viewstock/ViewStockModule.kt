package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.data.repo.WatchListRepository
import dagger.Module
import dagger.Provides

@Module
class ViewStockModule{

    @Provides
    fun presenter(repository: WatchListRepository) : ViewStockContract.Presenter{
        return ViewStockPresenter(repository)
    }

    @Provides
    fun watchListRepository(database: DatabaseManager): WatchListRepository {
        return WatchListRepository(database)
    }
}