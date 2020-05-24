package com.crazzyghost.stockmonitor.app

import com.crazzyghost.stockmonitor.ui.home.HomeComponent
import com.crazzyghost.stockmonitor.ui.search.SearchComponent
import com.crazzyghost.stockmonitor.ui.viewstock.ViewStockComponent
import dagger.Module

@Module(subcomponents = [
    HomeComponent::class,
    SearchComponent::class,
    ViewStockComponent::class
])
class SubcomponentsModule