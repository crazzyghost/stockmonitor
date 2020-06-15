package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.stockmonitor.app.AppExecutors
import com.crazzyghost.stockmonitor.app.ThreadPoolManager
import dagger.Module
import dagger.Provides

@Module
class ViewStockModule{

    @Provides
    fun presenter(executors: ThreadPoolManager) : ViewStockContract.Presenter{
        return ViewStockPresenter(executors)
    }
}