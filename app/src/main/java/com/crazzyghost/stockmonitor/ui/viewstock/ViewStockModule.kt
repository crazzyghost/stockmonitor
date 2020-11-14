package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.stockmonitor.app.ThreadPoolManager
import com.crazzyghost.stockmonitor.data.DatabaseManager
import dagger.Module
import dagger.Provides

@Module
class ViewStockModule{

    @Provides
    fun presenter(database: DatabaseManager) : ViewStockContract.Presenter{
        return ViewStockPresenter(database)
    }
}