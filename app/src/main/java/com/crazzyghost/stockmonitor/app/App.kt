package com.crazzyghost.stockmonitor.app

import android.app.Application
import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.Config
import com.crazzyghost.stockmonitor.data.DatabaseManager
import com.crazzyghost.stockmonitor.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Inject

class App : Application(), HasAndroidInjector {


    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>
    @Inject
    lateinit var appDatabase: DatabaseManager

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
                .inject(this)

        appDatabase.setupCompanyList()
        setupAlphavantage()
    }


    override fun androidInjector(): AndroidInjector<Any?>? {
        return injector
    }

    private fun setupAlphavantage(){

        val throttlingInterceptor : Interceptor = Interceptor.invoke {
            Thread.sleep(1000)
            it.proceed(it.request())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor = throttlingInterceptor)
            .build()

        val config = Config.Builder()
            .httpClient(client)
            .key("DEMOAPIK3Y")
            .build()

        AlphaVantage.api().init(config)

    }


}
