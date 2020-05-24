package com.crazzyghost.stockmonitor.app

import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.ui.home.HomeComponent
import com.crazzyghost.stockmonitor.ui.search.SearchComponent
import com.crazzyghost.stockmonitor.ui.viewstock.ViewStockComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, SubcomponentsModule::class])
interface AppComponent {
    fun homeComponent() : HomeComponent.Factory
    fun searchComponent() : SearchComponent.Factory
    fun viewStockComponent(): ViewStockComponent.Factory
    fun database(): DatabaseManager
}