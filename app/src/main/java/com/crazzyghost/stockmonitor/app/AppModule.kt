package com.crazzyghost.stockmonitor.app

import android.content.Context
import com.crazzyghost.stockmonitor.data.AppDatabaseManager
import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.di.ActivityModule
import com.crazzyghost.stockmonitor.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ActivityModule::class])
class AppModule constructor(private val context: Context){


    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideDatabaseManager(@ApplicationContext context: Context):
            DatabaseManager = AppDatabaseManager(context)

}