package com.crazzyghost.stockmonitor.ui.viewstock

import dagger.Module
import dagger.Provides

@Module
class ViewStockModule{

    @Provides
    fun presenter() : ViewStockContract.Presenter{
        return ViewStockPresenter()
    }
}