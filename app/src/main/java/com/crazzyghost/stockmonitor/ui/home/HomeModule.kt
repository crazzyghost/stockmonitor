package com.crazzyghost.stockmonitor.ui.home

import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @Provides
    fun presenter() : HomeContract.Presenter {
        return HomePresenter()
    }
}