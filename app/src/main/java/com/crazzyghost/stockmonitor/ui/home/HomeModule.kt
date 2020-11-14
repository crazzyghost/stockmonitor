package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.data.repo.WatchListRepository
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun presenter(repository: WatchListRepository) : HomeContract.Presenter {
        return HomePresenter(repository)
    }
}