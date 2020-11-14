package com.crazzyghost.stockmonitor.app

import android.app.Application
import android.content.Context
import com.crazzyghost.stockmonitor.annotations.ApplicationContext
import com.crazzyghost.stockmonitor.data.AppDatabaseManager
import com.crazzyghost.stockmonitor.data.DatabaseManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule constructor(private val app: Application){


    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideDatabaseManager(@ApplicationContext context: Context):
            DatabaseManager = AppDatabaseManager(context)

}