package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.app.ThreadPoolManager
import com.crazzyghost.stockmonitor.data.DatabaseManager
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun presenter(executors: ThreadPoolManager, database: DatabaseManager) : HomeContract.Presenter {
        return HomePresenter(executors, database)
    }
}