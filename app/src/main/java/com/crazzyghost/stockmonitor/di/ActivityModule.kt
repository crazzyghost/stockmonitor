package com.crazzyghost.stockmonitor.di

import com.crazzyghost.stockmonitor.ui.home.Home
import com.crazzyghost.stockmonitor.ui.home.HomeModule
import com.crazzyghost.stockmonitor.ui.search.Search
import com.crazzyghost.stockmonitor.ui.search.SearchModule
import com.crazzyghost.stockmonitor.ui.viewstock.ViewStock
import com.crazzyghost.stockmonitor.ui.viewstock.ViewStockModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun home(): Home

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchModule::class])
    abstract fun search(): Search

    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewStockModule::class])
    abstract fun viewStock(): ViewStock

}