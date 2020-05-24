package com.crazzyghost.stockmonitor.ui.search

import com.crazzyghost.stockmonitor.annotations.ActivityScope
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules=[SearchModule::class])
interface SearchComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SearchComponent
    }

    fun inject(activity: Search)
}