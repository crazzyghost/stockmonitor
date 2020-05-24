package com.crazzyghost.stockmonitor.ui.viewstock

import com.crazzyghost.stockmonitor.annotations.ActivityScope
import dagger.Subcomponent


@ActivityScope
@Subcomponent(modules = [ViewStockModule::class])
interface ViewStockComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): ViewStockComponent
    }

    fun inject(activity: ViewStock)
}