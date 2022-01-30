package com.crazzyghost.stockmonitor.di

import com.crazzyghost.stockmonitor.app.App
import com.crazzyghost.stockmonitor.app.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AndroidInjectionModule::class, AppModule::class]
)
interface AppComponent {
    fun inject(app: App)
}