package com.crazzyghost.stockmonitor.app

import android.app.Application
import com.crazzyghost.alphavantage.AlphaVantage
import com.crazzyghost.alphavantage.Config
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

        component.database().setupCompanyList()
        setupAlphavantage()
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
            .key("LNBH7P408KOXUUUD")
            .build()

        AlphaVantage.api().init(config)

    }


}