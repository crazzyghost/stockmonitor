package com.crazzyghost.stockmonitor.ui.home

import com.crazzyghost.stockmonitor.annotations.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): HomeComponent
    }

    fun inject(activity: Home)
}