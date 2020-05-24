package com.crazzyghost.stockmonitor.app

import android.app.Application

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

        component.database().setupCompanyList()
    }


}